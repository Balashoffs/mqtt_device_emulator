package com.balashoff.services;

import com.balashoff.dto.PowerSensor;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.generator.PowerGenerator;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;

@Log4j2
public class PowerService extends BaseService {
    protected final JsonAnalyzer<PowerSensor> jsonAnalyzer = new JsonAnalyzer<>();
    private final PowerGenerator pg = new PowerGenerator();

    public PowerService() {
        super();
    }

    public void push() {
        new Thread(() -> {
            while (customClient.isRunning.get()) {
                for (String topic : topics) {
                    double voltage = pg.generateVoltage();
                    double current = pg.generateCurrent();
                    double pw = voltage * current / 1000;
                    pw = pw * 100;
                    pw = Math.round(pw);
                    pw = pw / 100;
                    double power = pw;
                    double voltageFrequency = pg.generateVoltageFrequency();
                    double powerFactor = pg.generatePowerFactor();

                    PowerSensor ps = PowerSensor
                            .builder()
                            .name(topic)
                            .power(power)
                            .voltage(voltage)
                            .current(current)
                            .powerFactor(powerFactor)
                            .voltageFrequency(voltageFrequency)
                            .build();
                    String json = jsonAnalyzer.toJsonC(ps, PowerSensor.class);
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
