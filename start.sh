#!/bin/bash -e
cd "Starting postgres docker container..."
cd "$PWD/docker"
docker-compose stop && docker-compose rm -f && docker-compose build && docker-compose up -d

if docker ps --filter name=currencystock-postgresql | grep currencystock-postgresql
then
    echo "currencystock-postgresql container is running"
else
    echo "Not running!"
    exit 1
fi

cd ../
cd "Starting web application..."
./gradlew clean bootRun
function cleanup {
  echo "Clean up"
  cd "$PWD/docker"
  docker-compose stop && docker-compose rm -f
}

trap cleanup EXIT