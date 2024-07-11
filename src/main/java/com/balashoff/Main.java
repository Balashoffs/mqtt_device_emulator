package com.balashoff;

import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.services.BaseService;
import com.balashoff.services.ServiceFabric;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws MqttException {
        String host = "127.0.0.1";
        int port = 1883;
        if(args.length > 0){
            if(args.length == 2){
                host = args[0];
                String portArg = args[1];
                port = Integer.parseInt(portArg);
            }
        }
        MqttCustomClient customClient = new MqttCustomClient(host, port);
        ServiceFabric serviceFabric = new ServiceFabric();
        serviceFabric.init("devices.json");
        List<BaseService> bss  = serviceFabric.create(customClient);
        bss.forEach(baseService -> new Thread(baseService).start());
    }
}