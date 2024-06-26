#!/bin/sh

# Inicie o Redis em segundo plano
redis-server /etc/redis/redis.conf &

# Inicie a aplicação Spring Boot
java -jar /app/app.jar
