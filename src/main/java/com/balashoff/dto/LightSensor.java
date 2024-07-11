package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LightSensor {
    private String name;
    private  boolean isOn;
}
