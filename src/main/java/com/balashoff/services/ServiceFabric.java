package com.balashoff.services;

import com.balashoff.dto.StandDevice;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.ResourceReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceFabric {
    List<StandDevice> devices = new ArrayList<>();

    public void init(String filePath) {
        ResourceReader resourceReader = new ResourceReader();
        String json = resourceReader.readFileFromResource(filePath);
        JsonAnalyzer<List<StandDevice>> ja = new JsonAnalyzer<>();
        List<StandDevice> foundDevices = ja.fromJsonC(json, List.class, StandDevice.class);
        devices.addAll(foundDevices);
    }

    public List<BaseService> create(String mqttPath) {
        final Map<String, BaseService> createdServices = new HashMap<>();
        devices.forEach(standDevice -> {
            String topic = String.format("%s/%s", mqttPath, standDevice.getTopic());
            String type = standDevice.getType();

            switch (type) {
                case "light":
                    createdServices.putIfAbsent(type, new LightService());
                    break;
                case "curtains":
                    createdServices.putIfAbsent(type, new CurtainsService());
                    break;
                case "climate":
                    createdServices.putIfAbsent(type, new ClimateService());
                    break;
                case "power":
                    createdServices.putIfAbsent(type, new PowerService());
                    break;
            }
            if (createdServices.containsKey(type)) {
                createdServices.get(type).addTopic(topic);
            }

        });

        return createdServices.values().stream().toList();
    }
}
