package com.balashoff.dto;

import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimateSensor {
    private String temperature;
    private String humidity;
    private String pressure;
    private String tvoc;
    private String co2;

}
