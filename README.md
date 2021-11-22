# Sliding window rate limiter

Prerequisites
1. Java 1.8+

How to run
From project root directory run:

    mvn clean package
    java -jar target/RateLimiter-0.0.1-SNAPSHOT.jar

The route is configured to / of the server.

    curl http://localhost:{APP_PORT}

The default value for APP_PORT is 8080. It can be overridden by setting environment variable APP_PORT to the required port. Make requests to this API to get the count of request received in the server in last 60 seconds.

How to Test

1.From project root directory run : 

    mvn clean test
2.Vegeta load testing (https://github.com/tsenart/vegeta) : 

    echo "GET http://localhost:8080/" | vegeta attack -duration=5s -rate=2000 | tee results.bin | vegeta report

Running with Docker & docker-compose
Prerequisites
1. docker : https://docs.docker.com/engine/install/ 
2. docker-compose : https://docs.docker.com/compose/install/ 

Instructions

Run

    docker-compose up

This will build docker and run application in a docker container. Port mapping is done from 8080:8080 In case need to change the port on host, change the first argument to the required port, like 9000:8080
