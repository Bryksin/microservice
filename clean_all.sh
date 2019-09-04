#!/usr/bin/env bash
docker rm -f $(docker ps -aq)
docker system prun

if [[ "$1" == "images" ]]; then
    echo "Removing all docker images"
    docker rmi -f $(docker images -aq)
    docker system prune -f
fi