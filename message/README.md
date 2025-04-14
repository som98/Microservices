# Event Driven Message Microservice ->
## Message microservice uses both RabbitMQ and Apache Kafka as message brokers.

### Official documentation of Spring Cloud Function -
#### https://spring.io/projects/spring-cloud-function
#### https://docs.spring.io/spring-cloud-function/reference/

----------------------------------------------------------------------------------------------------------------------------------------------------

Apache Kafka Quickstart - https://kafka.apache.org/quickstart

Step 1: Get the Kafka Downloadable File ->
a. Place the extracted zip file of Apache Kafka under C: Drive. Not under any sub folders. (For me kept at C:\kafka - Renamed the folder)

b. Create a /.bashrc or /.bash_profile under the C:\Users\<YourUsername>\ directory (if not present).
Add the below line and save (This way it will be automatically applied in every cmd terminal session) -
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///C:/kafka/config/tools-log4j2.yaml"


Step 2: Start the Kafka environment ->
a. Generate a Cluster UUID (Run the below command) -
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"

b. Format the Kafka metadata storage directory and initialize the Kafka Cluster with a Unique Cluster ID (Run the below command) -
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties

c. Start the Kafka Server (Run the below command) -
bin/kafka-server-start.sh config/server.properties

Step 3: Create a topic to store your events ->
a. Create a new topic (Open another terminal session and Run the below command) -
bin/kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

b. To see the details such as the partition count of the new topic (Run the below command) -
bin/kafka-topics.sh --describe --topic my-topic --bootstrap-server localhost:9092

Step 4: Write some events into the topic (Run the below command) ->
bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092
(Write messages for events for each lines and hit Enter)

Step 5: Read the events ->
a. Run the console consumer client to read the events that were just created (Run the below command) -
bin/kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092

Step 6: Terminate the Kafka environment
a. Stop the producer and consumer clients and Kafka broker server with Ctrl+C
b. To delete any data in the local Kafka environment including any events the have been created (Run the below command) ->
rm -rf /c/tmp/kafka-logs /c/tmp/kraft-combined-logs