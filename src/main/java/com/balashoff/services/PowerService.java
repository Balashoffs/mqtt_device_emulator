package com.balashoff.services;

import com.balashoff.dto.PowerSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.PowerGenerator;

public class PowerService extends BaseService {
    protected final JsonAnalyzer<PowerSensor> jsonAnalyzer = new JsonAnalyzer<>();
    private final PowerGenerator pg = new PowerGenerator();

    public PowerService(MqttCustomClient customClient) {
        super(customClient);
    }

    public void push() {
        new Thread(()->{
            while (true){
                topics.forEach(topic ->{
                    double voltage = pg.generateVoltage();
                    double current = pg.generateCurrent();
                    double pw = voltage * current /1000;
                    pw = pw * 100;
                    pw = Math.round(pw);
                    pw = pw / 100;
                    double power = pw;
                    double voltageFrequency = pg.generateVoltageFrequency();
                    double powerFactor = pg.generatePowerFactor();

                    PowerSensor ps = PowerSensor
                            .builder()
                            .power(power)
                            .voltage(voltage)
                            .current(current)
                            .powerFactor(powerFactor)
                            .voltageFrequency(voltageFrequency)
                            .build();
                    String json = jsonAnalyzer.toJsonC(ps, PowerSensor.class);

                    customClient.pushMessage(topic, json);

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } );
            }
        }).start();
    }
}
