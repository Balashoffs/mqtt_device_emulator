package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurtainsSensor {
    private String name;
    /*
    -1 - backward
    0 - stop
    1 - forward
     */
    private  int direction;
}
