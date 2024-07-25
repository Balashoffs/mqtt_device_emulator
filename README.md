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