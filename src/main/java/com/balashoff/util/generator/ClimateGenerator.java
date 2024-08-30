package com.balashoff.util.generator;

public class ClimateGenerator extends BaseGenerator {
    private double minTemp = 18.0, maxTemp = 25.0;
    private double minHumidity = 45, maxHumidity = 85;
    private double minPressure = 745, maxPressure = 765;
    private double minCo2 = 500, maxCo2 = 1000;
    private double minTVOC = 500, maxTVOC = 1000;

    public String generateTemperature(){
        double value = generate(minTemp, maxTemp);
        return String.valueOf(round(value));
    }

    public String generateHumidity(){
        double value = generate(minHumidity, maxHumidity);
        return String.valueOf(round(value));
    }

    public String generatePressure(){
        double value =  generate(minPressure, maxPressure);
        return String.valueOf(round(value));
    }

    public String generateCo2(){
        double value = generate(minCo2, maxCo2);
        return String.valueOf(round(value));
    }

    public String generateTVOC(){
        double value =  generate(minTVOC, maxTVOC);
        return String.valueOf(round(value));
    }

}
