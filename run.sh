#!/usr/bin/env bash

./gradlew task clean

./gradlew task buildFatJar

scp ./build/libs/mqtt-emulator-latest.jar $1:/home/fuha/mqtt_emulator/app.jar

