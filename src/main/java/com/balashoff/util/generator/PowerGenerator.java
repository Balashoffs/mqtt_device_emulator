package com.balashoff.util.generator;

import java.util.Random;

public class PowerGenerator extends BaseGenerator{
    private double minVoltage = 220.0, maxVoltage = 230;
    private double minPower = 1.0, maxPower = 100.0;

    public double generateVoltage(){
        double value =  generate(minVoltage, maxVoltage);
        return  round(value);
    }

    public double generatePower(){
        double value =  generate(minPower, maxPower);
        return  round(value);
    }
}
