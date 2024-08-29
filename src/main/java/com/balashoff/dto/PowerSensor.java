package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PowerSensor {
    private double power;
    private double voltage;
    private double current;
    private double powerFactor;
    private double voltageFrequency;
}
