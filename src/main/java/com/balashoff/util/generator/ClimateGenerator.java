package com.balashoff.util.generator;

import com.balashoff.services.BaseService;

import java.util.Random;

public class ClimateGenerator extends BaseGenerator {
    private double minTemp = 18.0, maxTemp = 25.0;
    private double minHumidity = 45, maxHumidity = 85;
    private double minPressure = 745, maxPressure = 765;
    private double minCo2 = 500, maxCo2 = 1000;
    private double minTVOC = 500, maxTVOC = 1000;

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

    public double generateCo2(){
        double value = generate(minCo2, maxCo2);
        return round(value);
    }

    public double generateTVOC(){
        double value =  generate(minTVOC, maxTVOC);
        return round(value);
    }

}
