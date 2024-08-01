package com.balashoff.services;

import com.balashoff.dto.ClimateSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.ClimateGenerator;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClimateService extends BaseService
{
    private final ClimateGenerator climateGenerator = new ClimateGenerator();
    protected final JsonAnalyzer<ClimateSensor> jsonAnalyzer = new JsonAnalyzer<>();

    public ClimateService( MqttCustomClient customClient) {
        super(customClient);
    }


    public void push() {
        new Thread(()->{
            while (true){
                topics.forEach(topic -> {
                    double t = climateGenerator.generateTemperature();
                    double p = climateGenerator.generatePressure();
                    double h = climateGenerator.generateHumidity();
                    double c = climateGenerator.generateCo2();
                    double tv = climateGenerator.generateTVOC();
                    ClimateSensor cl = new ClimateSensor(topic, t, h, p, c, tv);
                    String json = jsonAnalyzer.toJsonC(cl, ClimateSensor.class);
                    customClient.pushMessage(topic, json);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        log.warn(e.getMessage());
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            }
        }).start();
    }


}
