#!/bin/bash

# Define an array of project directories
PROJECTS=(
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\Accounts"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\cards"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\loans"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\configserver"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\eurekaserver"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\gatewayserver"
    "C:\Users\SOMSHUBHRA ROY\Self Projects\Microservices\message"
)

# Loop through each project directory and run mvn clean
for PROJECT_DIR in "${PROJECTS[@]}"; do
    echo "Running 'mvn clean' for: $PROJECT_DIR"
    
    [ -d "$PROJECT_DIR" ] || { echo "Directory not found: $PROJECT_DIR"; continue; }

    (cd "$PROJECT_DIR" && mvn clean) || echo "Failed to clean project: $PROJECT_DIR"

    echo "---------------------------------"
done
