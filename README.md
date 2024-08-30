_**MQTT Emulator for setup and debug FUXA application**_

**Input arguments:**
```
 -ip - IP MQTT server (default - "127.0.0.1")
 -port - port MQTT server (default - 1883)
 -mqtt - root MQTT path (default - "bimstand")
```
**JDK - 17** 
```
build system - gradle
```
**Building**
```
Gradle -> shadow -> shadowJar

   Windows
      call .\gradlew.bat buildFatJar
   UNIX
      ./gradlew buildFatJar
```
**Run artifact**

```
java -jar mqtt-emulator*.jar -ip XXX.XXX.XXX.XXX -port YYYYY -mqtt SSSSSSSS`
```

**Build script**
```
UNIX 
   ./run.sh x.y.z XXX.XXX.XXX.XXX

WINDOWS
   .\run.bat x.y.z XXX.XXX.XXX.XXX

   - x.y.z - build version
   - XXX.XXX.XXX.XXX - ip address of raspberry host
```
**Create service**

1. Create file for unix service

```
sudo mkdir /etc/systemd/system/mqtt_emulator.service`
```

2. Fill created file from 'mqtt_emulator.service'


3. Execute command to enable service
   
```
sudo systemctl enable mqtt_emulator.service`
```

4. Execute command to Restart systemctl

```
sudo systemctl daemon-reload
```

5. Start/Stop service

```
sudo systemctl start javasimple.service
sudo systemctl status javasimple.service
```
6. Reboot UNIX system

** Install Jetbrain JDK**

1. Download JDK by JetBrain

```
https://cache-redirector.jetbrains.com/intellij-jbr/jbrsdk-21.0.3-linux-aarch64-b509.11.tar.gz
```

2. Unzip archive

```
tar -xvzf jbrsdk-21.0.3-linux-aarch64-b509.11.tar.gz
```


3. Add Java bin to PATH - '~/.bashrc'

```
JAVA_HOME="/home/fuha/fuha/jbrsdk-21.0.3-linux-aarch64-b509.11"
PATH=$PATH:$JAVA_HOME/bin`
export JAVA_HOME`
export PATH
```

4. Execute command to reload

```
source .bashrc
```


   