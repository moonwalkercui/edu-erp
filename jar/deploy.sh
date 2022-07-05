#!/bin/bash

# 本脚本的作用是停止当前Spring Boot应用，然后再次部署
PID=$(ps -ef | grep hzb-base-api.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi

cd /www/wwwroot/hzb-erp/ || exit 2
nohup sudo java -jar hzb-base-api.jar > log.log 2>&1 &