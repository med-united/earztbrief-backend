#!/bin/bash
sudo systemctl stop med-united
sudo systemctl disable med-united.service
sudo rm -R /opt/med-united
sudo rm /etc/systemd/system/med-united.service
echo "ere-health successfully uninstalled"