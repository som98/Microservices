#!/bin/bash

# Run all 3 MySQL containers

echo "Starting accountsDB..."
docker run -p 3306:3306 --name accountsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsDB -d mysql:latest

echo "Starting cardsDB..."
docker run -p 3307:3306 --name cardsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsDB -d mysql:latest

echo "Starting loansDB..."
docker run -p 3308:3306 --name loansDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansDB -d mysql:latest

echo "Starting redis..."
docker run -p 6379:6379 --name redis -d redis:latest

echo "All containers started successfully!"
