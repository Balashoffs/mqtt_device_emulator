package com.balashoff.util.generator;

import java.util.Random;

abstract public class BaseGenerator {
    private final Random random = new Random();

    protected final double generate(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    protected double round(double value) {
        String d = String.format("%.2f", value).replace(",", ".");
        return Double.parseDouble(d);
    }
}
