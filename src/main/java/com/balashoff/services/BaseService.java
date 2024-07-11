package com.balashoff.services;

import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import lombok.AllArgsConstructor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@AllArgsConstructor
abstract public class BaseService implements Runnable {
    protected String topic;
    protected MqttCustomClient customClient;
    protected final Executor executor = Executors.newSingleThreadExecutor();

}
