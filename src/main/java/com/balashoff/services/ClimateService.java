package com.balashoff.services;

import com.balashoff.dto.ClimateSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.ClimateGenerator;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;

@Log4j2
public class ClimateService extends BaseService {
    private final ClimateGenerator climateGenerator = new ClimateGenerator();
    protected final JsonAnalyzer<ClimateSensor> jsonAnalyzer = new JsonAnalyzer<>();

    public ClimateService() {
        super();
    }


    @Override
    public void stop() {

    }

    public void push() {
        new Thread(() -> {
            while (customClient.isRunning.get()) {
                for (String topic : topics) {
                    double t = climateGenerator.generateTemperature();
                    double p = climateGenerator.generatePressure();
                    double h = climateGenerator.generateHumidity();
                    double c = climateGenerator.generateCo2();
                    double tv = climateGenerator.generateTVOC();
                    ClimateSensor cl = new ClimateSensor(topic, t, h);
                    String json = jsonAnalyzer.toJsonC(cl, ClimateSensor.class);
                    boolean isPushed = customClient.pushMessage(topic, json);
                    if(!isPushed) break;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        log.warn(e.getMessage());
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            }
        }).start();
    }


}
