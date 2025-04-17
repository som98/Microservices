#!/bin/bash

# Step 0: Move to Kafka directory
echo "Navigating to Kafka installation directory..."
cd /c/kafka || { echo "Kafka directory not found!"; exit 1; }

# Step 1: Clean old Kafka metadata/logs
echo "Removing all old logs..."
rm -rf /c/tmp/kafka-logs /c/tmp/kraft-combined-logs

# Step 2: Generate new cluster ID
echo "Generating new cluster ID..."
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
echo "Cluster ID: $KAFKA_CLUSTER_ID"

# Step 3: Format storage with new cluster ID
echo "Formatting storage..."
bin/kafka-storage.sh format --standalone -t "$KAFKA_CLUSTER_ID" -c config/server.properties

# Step 4: Start Kafka server
echo "Starting Kafka server..."
bin/kafka-server-start.sh config/server.properties
