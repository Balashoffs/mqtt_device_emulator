package com.balashoff;

import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.services.BaseService;
import com.balashoff.services.ServiceFabric;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws MqttException {
        String host = "127.0.0.1";
        String mqttPath = "bimstand";
        int port = 1883;
        if(args.length > 0){
            for (int i = 0; i< args.length; i+=2){
                String value = args[i];
                switch (value){
                    case "-ip":
                        host = args[i+1];
                        break;
                    case "-port":
                        port = Integer.parseInt(args[i+1]);
                        break;
                    case "-mqtt":
                        mqttPath = args[i+1];
                        break;
                }
            }
        }
        MqttCustomClient customClient = new MqttCustomClient(host, port);
        ServiceFabric serviceFabric = new ServiceFabric();
        serviceFabric.init("devices.json");
        List<BaseService> bss  = serviceFabric.create(customClient, mqttPath);
        bss.forEach(baseService -> new Thread(baseService).start());
    }
}