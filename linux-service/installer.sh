#!/bin/bash
sudo cp -r ../target/quarkus-app /opt/med-united
sudo mkdir -p /opt/med-united/application/quarkus-app/
sudo mkdir -p /etc/med-united/config
sudo cp ../src/main/resources/application.properties /etc/med-united/config
sudo ln -s /etc/med-united/config /opt/med-united/config
sudo cp med-united.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable med-united.service
sudo systemctl start med-united
sudo systemctl status med-united