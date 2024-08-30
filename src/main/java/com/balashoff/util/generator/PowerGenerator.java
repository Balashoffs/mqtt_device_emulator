package com.balashoff.util.generator;

import java.util.Random;

public class PowerGenerator extends BaseGenerator {
    private double minVoltage = 220.0, maxVoltage = 230;
    private double minPower = 1.0, maxPower = 100.0;

    private double minCurrent = 1.0, maxCurrent = 1000;
    private double minPowerFactor = 0.8, maxPowerFactor = 1.0;
    private double minVoltageFrequency = 49.0, maxVoltageFrequency = 51.0;

    public double generateVoltage() {
        double value = generate(minVoltage, maxVoltage);
        return round(value);
    }

    public double generatePower() {
        double value = generate(minPower, maxPower);
        return round(value);
    }

    public double generateCurrent() {
        double value = generate(minCurrent, maxCurrent);
        return round(value);
    }

    public double generatePowerFactor() {
        double value = generate(minPowerFactor, maxPowerFactor);
        return round(value);
    }

    public double generateVoltageFrequency() {
        double value = generate(minVoltageFrequency, maxVoltageFrequency);
        return round(value);
    }
}
