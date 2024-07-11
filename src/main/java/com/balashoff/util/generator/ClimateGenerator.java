package com.balashoff.util.generator;

import com.balashoff.services.BaseService;

import java.util.Random;

public class ClimateGenerator extends BaseGenerator {
    private double minTemp = 18.0, maxTemp = 25.0;
    private double minHumidity = 45, maxHumidity = 85;
    private double minPressure = 745, maxPressure = 765;

    public double generateTemperature(){
        double value = generate(minTemp, maxTemp);
        return round(value);
    }

    public double generateHumidity(){
        double value = generate(minHumidity, maxHumidity);
        return round(value);
    }

    public double generatePressure(){
        double value =  generate(minPressure, maxPressure);
        return round(value);
    }

}
