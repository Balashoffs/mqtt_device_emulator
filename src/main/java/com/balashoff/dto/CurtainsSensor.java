package com.balashoff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurtainsSensor {
    /*
    -1 - backward
    0 - stop
    1 - forward
     */
    private int direction;
}
