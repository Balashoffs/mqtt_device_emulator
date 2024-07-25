package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StandDevice {
    private String topic;
    private String type;
    private String uuid;
}
