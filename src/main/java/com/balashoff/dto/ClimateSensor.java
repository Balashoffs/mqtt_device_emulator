package com.balashoff.dto;

import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimateSensor {
    private double temperature;
    private double humidity;
    private double tvoc;
    private double co2;
    private double pressure;
}
