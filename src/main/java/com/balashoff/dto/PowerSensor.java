package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PowerSensor {
    private String name;
    private double power;
    private double voltage;
}
