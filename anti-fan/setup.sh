#!/bin/bash
mkdir -p /opt/src

java   -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1005 -jar app.jar