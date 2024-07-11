package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimateSensor {
    private String name;
    private double temperature;
    private double humidity;
    private double pressure;
}
