#!/bin/bash

PID=$(ps -ef | grep hzb-base-api.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi