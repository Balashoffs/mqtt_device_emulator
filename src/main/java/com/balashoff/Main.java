package com.balashoff;

import com.balashoff.mqtt.MqttCustomClient;
import com.balashoff.services.BaseService;
import com.balashoff.services.ServiceFabric;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

@Log4j2
public class Main {
    public static void main(String[] args) {
        final SetupData data = SetupData.create(args);
        ServiceFabric serviceFabric = new ServiceFabric();
        serviceFabric.init("devices.json");
        List<BaseService> bss = serviceFabric.create(data.rootPath);

        while (true){
            MqttCustomClient customClient = new MqttCustomClient(data.host, data.port);
            while (true) {
                try {
                    customClient.connect();
                    break;
                } catch (MqttException e) {
                    log.error("Maybe mqtt server not startes at {}:{}", data.host, data.port);
                    log.error(e.fillInStackTrace());
                }
                try {
                    log.warn("Waiting for 10 sec for retry to connect to mqtt server!");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error(e.fillInStackTrace());
                }
            }

            bss.forEach(baseService -> {
                baseService.set(customClient);
                new Thread(baseService).start();
            });

            while (customClient.isRunning.get()){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e.fillInStackTrace());
                }
            }
            log.info("Restart emulator mqtt devices");
        }
    }
}

@AllArgsConstructor
class SetupData {
    final String host;
    final int port;
    final String rootPath;

    static SetupData create(String[] args) {
        String host = "127.0.0.1";
        String mqttPath = "bimstand";
        int port = 1883;
        if (args.length > 0) {
            for (int i = 0; i < args.length; i += 2) {
                String value = args[i];
                switch (value) {
                    case "-ip":
                        host = args[i + 1];
                        break;
                    case "-port":
                        port = Integer.parseInt(args[i + 1]);
                        break;
                    case "-mqtt":
                        mqttPath = args[i + 1];
                        break;
                }
            }
        }
        return new SetupData(host, port, mqttPath);
    }
}