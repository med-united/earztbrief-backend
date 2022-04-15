#!/bin/bash
cp -r ../target/quarkus-app /opt/med-united
mkdir -p /opt/med-united/application/quarkus-app/
mkdir -p /etc/med-united/config
cp ../src/main/resources/application.properties /etc/med-united/config
ln -s /etc/med-united/config /opt/med-united/config
#!mv /opt/med-united/server.keystore /opt/med-united/application/quarkus-app/server.keystore
cp med-united.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable med-united.service
sudo systemctl start med-united
sudo systemctl status med-united