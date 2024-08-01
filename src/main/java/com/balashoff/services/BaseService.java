package com.balashoff.services;

import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@AllArgsConstructor
abstract public class BaseService implements Runnable {

    protected MqttCustomClient customClient;
    protected final List<String> topics = new ArrayList<>();

    public  void addTopic(String topic){
        topics.add(topic);
    }

    public void push(){}
    public void poll(){}

    @Override
    public void run() {
        push();
        poll();
    }
}
