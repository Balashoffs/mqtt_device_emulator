package com.balashoff.services;


import com.balashoff.dto.LightSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LightService extends BaseService {
    protected final JsonAnalyzer<LightSensor> jsonAnalyzer = new JsonAnalyzer<>();
    public LightService(MqttCustomClient customClient) {
        super(customClient);
    }

    @Override
    public void poll() {
        topics.forEach(topic -> {
            customClient.subscribeTopic(topic, s -> {
                log.info("Входящее сообщение: {}", s);
                LightSensor lightSensor = jsonAnalyzer.fromJsonC(s, LightSensor.class);
                log.info("{}: Свет {}", topic,  lightSensor.isOn() ? "включен" : "выключен");
            });
        });
    }
}
