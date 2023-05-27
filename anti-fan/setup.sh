#!/bin/bash
mkdir -p /opt/src

java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=56m -Xms128m -Xmx128m -Xmn32m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC  -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1005 -jar app.jar