#!/bin/bash

# 重新部署服务
cd /www/wwwroot/hzb-erp/ || exit 2
sudo ./stop.sh
sudo ./deploy.sh