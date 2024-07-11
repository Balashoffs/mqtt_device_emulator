package com.balashoff.services;

import com.balashoff.dto.ClimateSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.ClimateGenerator;

public class ClimateService extends BaseService
{
    private final ClimateGenerator climateGenerator = new ClimateGenerator();
    protected final JsonAnalyzer<ClimateSensor> jsonAnalyzer = new JsonAnalyzer<>();

    public ClimateService(String topic, MqttCustomClient customClient) {
        super(topic, customClient);
    }

    @Override
    public void run() {
        while (true){
            double t = climateGenerator.generateTemperature();
            double p = climateGenerator.generatePressure();
            double h = climateGenerator.generateHumidity();

            ClimateSensor cl = new ClimateSensor(topic, t, h, p);
            String json = jsonAnalyzer.toJsonC(cl, ClimateSensor.class);

            customClient.pushMessage(topic, json);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
