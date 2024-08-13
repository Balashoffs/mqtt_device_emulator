_**MQTT Emulator for setup and debug FUXA application**_

**input arguments:**

    -ip - IP MQTT server (default - "127.0.0.1")
    -port - port MQTT server (default - 1883)
    -mqtt - root MQTT path (default - "bimstand")

**JDK - 17** 

    build system - gradle

**building**

    Gradle -> shadow -> shadowJar

**run artifact**

    `java -jar mqtt-emulator*.jar -ip XXX.XXX.XXX.XXX -port YYYYY -mqtt SSSSSSSS`


**Run Java app ass Service**

1. Write a unit file for systemd.
   
        $ sudo nano /etc/systemd/system/your_jar_software.service


        [Unit]
        Description=Example on starting a JAR with systemd
        After=network.target
    
        [Service]
        Type=simple
        ExecStart=/usr/bin/java -jar /home/pi/path/to/your/JAR.jar
    
        [Install]
        WantedBy=multi-user.target
2. Start it at boot
   
       $ sudo systemctl enable your_jar_software

3. Manually control it

       $ sudo systemctl start|stop|status|restart your_jar_software

4. Observe the output
      
       $ sudo journalctl -u your_jar_software

**Copy to raspberry**

        scp mqtt-emulator-latest.jar fuha@192.168.88.189:/home/fuha/mqtt_emulator/app.jar

**Run script**
   
      ./run fuha@192.168.88.189