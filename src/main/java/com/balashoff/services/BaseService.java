package com.balashoff.services;

import com.balashoff.mqtt.MqttCustomClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

abstract public class BaseService implements Runnable {

    protected final List<String> topics = new ArrayList<>();

    protected MqttCustomClient customClient;

    public void set(MqttCustomClient customClient) {
        this.customClient = customClient;
    }

    public void addTopic(String topic) {
        topics.add(topic);
    }


    public void push() {
    }

    public void poll() {
    }

    public void stop() {
    }

    @Override
    public void run() {
        push();
        poll();
    }
}
