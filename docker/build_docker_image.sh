#!/usr/bin/env bash

cp ../build/libs/*.jar ./app.jar
docker build -t elasticsearch-prototype .
rm ./app.jar