package com.balashoff.dto;

import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimateSensor {
    private String name;
    private double temperature;
    private double humidity;
    private double pressure;
    private double tvoc;
    private double co2;
}
