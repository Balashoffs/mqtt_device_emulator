echo off
set arg1=%1
set arg2=%2

call .\gradlew.bat clean
call .\gradlew.bat buildFatJar

scp .\build\libs\mqtt-emulator-%arg1%.jar fuha@%arg2%:/home/fuha/fuha/mqtt_emulator/app.jar

