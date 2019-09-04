#!/usr/bin/env bash
docker-compose down

if [[ "$1" == "clean" ]]; then
    if [[ "$2" == "images" ]]; then
        ./clean_all.sh images
    else
        ./clean_all.sh
    fi
fi