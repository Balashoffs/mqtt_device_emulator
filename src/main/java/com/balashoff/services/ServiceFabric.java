package com.balashoff.services;

import com.balashoff.dto.StandDevice;
import com.balashoff.json.JsonAnalyzer;
import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.util.ResourceReader;

import java.util.ArrayList;
import java.util.List;

public class ServiceFabric {
    List<StandDevice> devices = new ArrayList<>();

    public void init(String filePath) {
        ResourceReader resourceReader = new ResourceReader();
        String json = resourceReader.readFileFromResource(filePath);
        JsonAnalyzer<List<StandDevice>> ja = new JsonAnalyzer<>();
        List<StandDevice> foundDevices = ja.fromJsonC(json, List.class, StandDevice.class);
        devices.addAll(foundDevices);
    }

    public List<BaseService> create(MqttCustomClient customClient, String mqttPath) {
        final List<BaseService> createdServices = new ArrayList<>();
        devices.forEach(standDevice -> {
            String topic = String.format("%s/%s", mqttPath, standDevice.getTopic());
            switch (standDevice.getType()) {
                case "light":
                    BaseService ls = new LightService( topic, customClient);
                    createdServices.add(ls);
                    break;
                case "curtains":
                    BaseService crs = new CurtainsService( topic, customClient);
                    createdServices.add(crs);
                    break;
                case "climate":
                    BaseService cs = new ClimateService(topic, customClient);
                    createdServices.add(cs);
                    break;
                case "power":
                    BaseService ps = new PowerService(topic, customClient);
                    createdServices.add(ps);
                    break;
            }
        });

        return createdServices;
    }
}
