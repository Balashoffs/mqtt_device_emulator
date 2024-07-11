package com.balashoff.services;

import com.balashoff.dto.PowerSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.PowerGenerator;

public class PowerService extends BaseService{
    protected final JsonAnalyzer<PowerSensor> jsonAnalyzer = new JsonAnalyzer<>();
    private final PowerGenerator pg = new PowerGenerator();
    public PowerService(String topic, MqttCustomClient customClient) {
        super(topic, customClient);
    }

    @Override
    public void run() {
        while (true){
            double voltage = pg.generateVoltage();
            double power = pg.generatePower();

            PowerSensor ps = PowerSensor.builder().power(power).voltage(voltage).build();
            String json = jsonAnalyzer.toJsonC(ps, PowerSensor.class);

            customClient.pushMessage(topic, json);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
