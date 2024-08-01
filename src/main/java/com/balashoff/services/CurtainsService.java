package com.balashoff.services;


import com.balashoff.dto.CurtainsSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Log4j2
public class CurtainsService extends BaseService {
    private final AtomicInteger status = new AtomicInteger(0);
    private final AtomicInteger currentPos = new AtomicInteger(0);
    private static final int qnt = 10;

    protected final JsonAnalyzer<CurtainsSensor> jsonAnalyzer = new JsonAnalyzer<>();

    public CurtainsService(MqttCustomClient customClient) {
        super(customClient);
    }

    private void up() {
        log.info("Пытаемся открыть штору");
        int curr = currentPos.intValue();
        for (int i = curr; i < qnt; i++) {
            if (status.get() == 0) {
                log.info("Остановка движении шторы");
                break;
            }
            log.info("Открытие шторы, шаг: {}", i);
            currentPos.set(i + 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (i + 1 == qnt) {
                log.info("Штора открыта");
                status.set(1);
            }
        }

    }

    private void down() {
        log.info("Пытаемся закрыть штору");
        int curr = currentPos.intValue();
        for (int i = curr; i > 0; i--) {
            if (status.get() == 0) {
                log.info("Остановка движении шторы");
                break;
            }
            currentPos.set(i - 1);
            log.info("Закрытие шторы, шаг: {}", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (i - 1 == 0) {
                log.info("Штора закрыта");
                status.set(-1);
            }
        }
    }

    public void poll() {
        topics.forEach(topic ->{
            customClient.subscribeTopic(topic, s -> {
                log.info("Входящее сообщение: {}", s);
                CurtainsSensor incoming = jsonAnalyzer.fromJsonC(s, CurtainsSensor.class);
                int nextDirection = incoming.getDirection();
                int currentDirection = status.get();
                if (nextDirection == currentDirection ) {
                    log.info("Пользователь пытается повторить действие: {}", directionAsString(nextDirection));
                } else {
                    if (currentPos.get() > 0 && currentPos.get() < qnt) {
                        log.info("Остановка движении шторы");
                        status.set(0);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    status.set(nextDirection);
                    if (nextDirection == 1) {
                    } else if (nextDirection == -1) {
                    } else {
                        log.warn("Странное поведение, обратить внимание");
                    }
                }
            });
        } );
    }

    String directionAsString(int dir){
        return switch (dir) {
            case -1 -> "Опускание";
            case 1 -> "Поднятие";
            default -> "Пауза";
        };
    }
}

