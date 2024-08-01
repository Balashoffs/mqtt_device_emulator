package com.balashoff;

import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.services.BaseService;
import com.balashoff.services.ServiceFabric;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.net.ConnectException;
import java.util.List;

@Log4j2
public class Main {
    public static void main(String[] args){
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
        while (true){
            try{
                MqttCustomClient customClient = new MqttCustomClient(host, port);
                ServiceFabric serviceFabric = new ServiceFabric();
                serviceFabric.init("devices.json");
                List<BaseService> bss  = serviceFabric.create(customClient, mqttPath);
                bss.forEach(baseService -> new Thread(baseService).start());
                log.info("Run all services");
                break;
            }catch ( MqttException e){
                log.error("Maybe mqtt server not startes at {}:{}", host, port);
                log.error(e.fillInStackTrace());

            }
            try {
                log.warn("Waiting for 10 sec for retry to connect to mqtt server!");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.error(e.fillInStackTrace());
            }
        }
    }
}