#!/usr/bin/env bash

build_gradle() {

    echo "Building project jar's"
    ./gradlew clean build
}

run_management_only() {

    if [[ "$1" == "build" ]]; then
        build_gradle
        echo "Building Docker Images and Running only Management services:"
        docker-compose up --build mongodb config-service discovery-service
    else
        echo "Running only Management services:"
        docker-compose up mongodb config-service discovery-service
    fi
}

run_all_no_scale() {

    if [[ "$1" == "build" ]]; then
        build_gradle
        echo "Building Docker Images and running Docker-Compose - No Scale"
        docker-compose up --build
    else
        echo "Running Docker-Compose - No Scale"
        docker-compose up
    fi
}

run_all_with_scale() {

    if [[ $2 =~ '^[0-9]+$' ]]; then
        echo "Unexpected argument [$2] for 'scale', expected Number [0-9]"
        print_usage
        exit 1
    fi

    if [[ "$1" == "build" ]]; then
        build_gradle
        echo "Building Docker Images and running Docker-Compose - with scale $2"
        docker-compose up --build --scale student-service=$2 --scale teacher-service=$2 --scale class-service=$2
    else
        echo "Running Docker-Compose - with scale $2"
        docker-compose up --scale student-service=$2 --scale teacher-service=$2 --scale class-service=$2
    fi
}

print_usage() {

    echo ""
    echo ""
    echo "USAGE:"
    echo ""
    echo "./run.sh [all]                   - run all without build, no scale"
    echo ""
    echo "./run.sh all build"
    echo "./run.sh build                   - run all with build, no scale"
    echo ""
    echo "./run.sh management [build]      - run only management services no scale, [build] optionally"
    echo "                                 - discovery-service"
    echo "                                 - config-service"
    echo "                                 - mongo DB"
    echo ""
    echo "./run.sh scale nr [build]        - run all with services at scale of [nr] instances, [build] optionally"
    echo "                                 - teacher-service"
    echo "                                 - student-service"
    echo "                                 - class-service"
    echo ""
    echo ""
}

if [[ "$1" == "" ]] || [[ "$1" == "all" ]]; then
    run_all_no_scale $2

elif [[ "$1" == "build" ]]; then
    run_all_no_scale $1

elif [[ "$1" == "management" ]]; then
    run_management_only $2

elif [[ "$1" == "scale" ]]; then
    run_all_with_scale $3 $2

elif [[ "$1" == "help" ]] || [[ "$1" == "?" ]]; then
    print_usage
else
    echo "Unexpected argument [$1]"
    print_usage
fi
