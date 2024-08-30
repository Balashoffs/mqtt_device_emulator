#!/usr/bin/env bash

set arg1=$1
set arg2=$2

chmod +x gradlew

./gradlew clean
./gradlew buildFatJar

scp ./build/libs/mqtt-emulator-$arg1.jar fuha@$arg2:/home/fuha/fuha/mqtt_emulator/app.jar