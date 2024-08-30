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


    public void push() {
        while (true){
            topics.forEach(topic -> {
                String t = climateGenerator.generateTemperature();
                String p = climateGenerator.generatePressure();
                String h = climateGenerator.generateHumidity();
                String c = climateGenerator.generateCo2();
                String tv = climateGenerator.generateTVOC();
                ClimateSensor cl = new ClimateSensor( t, h, p, c, tv);
                String json = jsonAnalyzer.toJsonC(cl, ClimateSensor.class);
                customClient.pushMessage(topic, json);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
            }
        }
    }


}
