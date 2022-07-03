#!/bin/bash

PID=$(ps -ef | grep hzb-base-api-1.0.0.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi