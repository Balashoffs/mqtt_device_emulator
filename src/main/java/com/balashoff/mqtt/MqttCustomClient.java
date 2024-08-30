package com.balashoff.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Log4j2
@RequiredArgsConstructor
public class MqttCustomClient {
    private IMqttClient publisher;
    private final String host;
    private final int port;
    public AtomicBoolean isRunning = new AtomicBoolean(false);

    public void connect() throws MqttException {
        String publisherId = UUID.randomUUID().toString();
        publisher = new MqttClient(String.format("tcp://%s:%d", host, port), publisherId);

        log.debug("Create mqtt publisher");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        log.debug("Create mqtt option: {}", options.toString());
        publisher.connect(options);
        isRunning.set(true);

        log.debug("Mqtt publisher connected");
    }

    public void subscribeTopic(String topicName, Consumer<String> stringConsumer) {
        try {
            publisher.subscribe(topicName, (s, mqttMessage) -> {
                byte[] bytes = mqttMessage.getPayload();
                stringConsumer.accept(new String(bytes));
            });
            log.debug("Mqtt publisher subscribe to {}", topicName);
        } catch (MqttException e) {
            log.warn("Mqtt publisher connected");
            log.error(e.getMessage());
            log.error(e.getCause());
            isRunning.set(false);
            close();
        }

    }

    public void pushMessage(String topic, String message) {

        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(0);
            mqttMessage.setRetained(true);
            publisher.publish(topic, mqttMessage);
            log.debug("Mqtt publisher publish message: {}", message);
        } catch (MqttException e) {
            log.warn("Mqtt publisher publish message: {}", message);
            log.error(e.getMessage());
            log.error(e.getCause());
            isRunning.set(false);
        }

        isRunning.get();

    }

    public void unSubscribe(String s) {
        try {
            publisher.unsubscribe(s);
        } catch (MqttException e) {
            log.error(e.getMessage());
            log.error(e.getCause());
        }
    }


    public void close() {
        try {
            if (publisher != null) {
                publisher.close();
            }
        } catch (MqttException e) {
            log.error(e.getMessage());
            log.error(e.getCause());
        }
    }
}
