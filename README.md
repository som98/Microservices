# <u>Microservices Project</u>

--------------------------------------------------------------------------------------------------------------------------------------------------------------
### <u>GitHub repository link for the projects</u> - 
#### https://github.com/som98/Microservices
#### https://github.com/som98/Microservices-Config

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Configserver Microservice</u>
### The microservice is based on the concepts of Spring Cloud Config Server.
#### Official documentation of Spring Cloud - https://spring.io/projects/spring-cloud
#### Official documentations of Spring Cloud Config- https://spring.io/projects/spring-cloud-config, https://docs.spring.io/spring-cloud-config/reference/

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Gatewayserver Microservice</u>
### The microservice is based on the concepts of Spring Cloud Gateway.
#### Official documentation of Spring Cloud Gateway- https://docs.spring.io/spring-cloud-gateway/reference/

### Explanation of code concepts implemented using Spring Cloud Gateway Filters ->
#### 1. RequestTraceFilter.java:
- This class implements the `GlobalFilter` interface, which means it's part of the Spring Cloud Gateway filter chain.
- The purpose of this filter is to handle request tracing.
- When a request comes in, it checks if a correlation ID (e.g., `eazyBank-correlation-id`) is present in the request headers.
- If the correlation ID is present, it logs it.
- If not, it generates a new correlation ID using `java.util.UUID.randomUUID().toString()` and sets it in the request headers.
- Finally, it passes the request along the filter chain using `chain.filter(exchange)`.

#### 2. FilterUtility.java:
- This class provides utility methods related to filters.
- It defines a constant `CORRELATION_ID` representing the header key for the correlation ID.
- The `getCorrelationId(HttpHeaders requestHeaders)` method retrieves the correlation ID from the request headers.
- The `setRequestHeader(ServerWebExchange exchange, String name, String value)` method sets a custom header in the request.
- The `setCorrelationId(ServerWebExchange exchange, String correlationId)` method sets the correlation ID in the request headers.

#### 3. ResponseTraceFilter.java:
- This configuration class sets up a filter to handle response tracing.
- It logs the correlation ID from the request headers and adds it to the outbound response headers.

##### Overall, these filters help manage correlation IDs for tracing purposes in a Spring Cloud Gateway application. They ensure that requests are associated with a unique identifier, making it easier to track and analyze them across different services or components.

#### Official documentation links for Implementing Circuit Breaker - https://resilience4j.readme.io/docs/circuitbreaker, https://docs.spring.io/spring-cloud-circuitbreaker/reference/

#### Website Link to download Apache Benchmark (used in load testing for Redis Rate Limiter) - https://www.apachelounge.com/download/
#### Command for API load testing and performance benchmarking using Apache Benchmark - `ab -n 10 -c 2 -v 3 < url >` (url example -  _http://localhost:8072/sombank/cards/cards/contactInfo_)

#### Website Link for Keycloak Auth Server - https://www.keycloak.org/getting-started/getting-started-docker

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Message Microservice</u>
### The microservice is based on the concepts of Event Driven Models.
### Message microservice uses both <u>RabbitMQ</u> and <u>Apache Kafka</u> as message brokers.
#### Official documentation of Spring Cloud Function - https://spring.io/projects/spring-cloud-function, https://docs.spring.io/spring-cloud-function/reference/
#### Official documentation of Spring Cloud Stream - https://spring.io/projects/spring-cloud-stream, https://docs.spring.io/spring-cloud-stream/reference/

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Docker Run Commands</u> 
### MySQL DB Image and Container creation ->
`docker run -p 3306:3306 --name accountsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsDB -d mysql:latest`  
`docker run -p 3307:3306 --name cardsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsDB -d mysql:latest`  
`docker run -p 3308:3306 --name loansDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansDB -d mysql:latest`

### Run Adminer UI with linked MySQL container ->
`docker run -p 1000:8080 --link accountsDB:db -d adminer`  
`docker run -p 2000:8080 --link cardsDB:db -d adminer`  
`docker run -p 3000:8080 --link loans:db -d adminer`

### Run Adminer container connected to a network (Run [*docker network ls*] to find the network name) ->
`docker run -d --name adminerAccountsDB --network prod_somshubhra-roy -p 1000:8080 adminer`

###  RabbitMQ Image and Container creation ->
`docker run -dit --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management`

### Redis Image and Container creation ->
`docker run -p 6379:6379 --name redis -d redis:latest`

### Keycloak AuthServer Image and Container creation->
`docker run -p 7080:8080 -d -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev`
#### Keycloak Admin Console - http://localhost:7080/admin

### Image creation using Google Jib ->
`mvn compile jib:dockerBuild`

### To push images into Docker Hub ->
`docker image push docker.io/somshubhraroy/<appName>:<TagVersion>`

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Apache Kafka</u>
### Quickstart Link for Apache Kafka for Windows - https://kafka.apache.org/quickstart

### Apache Kafka Installation Guide ->
#### Step 1: Get the Kafka Downloadable File -
- Place the extracted zip file of Apache Kafka under C: Drive. Not under any sub folders. (I kept at C:\kafka - Renamed the folder)
- Create a `/.bashrc` or `/.bash_profile` under the `C:\Users\<YourUsername>\` directory (if not present). 
- Add the line and save (This way it will be automatically applied in every cmd terminal session) - `export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///C:/kafka/config/tools-log4j2.yaml"`

#### Step 2: Start the Kafka environment -
- Generate a Cluster UUID (Run the command) - `KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"`
- Format the Kafka metadata storage directory and initialize the Kafka Cluster with a Unique Cluster ID (Run the command) - 
  `bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties`
- Start the Kafka Server (Run below command) - `bin/kafka-server-start.sh config/server.properties`

#### Step 3: Create a topic to store your events -
- Create a new topic (Open another terminal session and Run the command) -
  `bin/kafka-topics.sh --create --topic <topicName> --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1`
- To see the details of the topic such as the partition count, etc. (Run the command) -
  `bin/kafka-topics.sh --describe --topic <topicName> --bootstrap-server localhost:9092`

#### Step 4: Write some events into the topic (Run the below command) -
- `bin/kafka-console-producer.sh --topic <topicName> --bootstrap-server localhost:9092`  
(Write messages for events for each lines and hit Enter)

#### Step 5: Read the events -
- Run the console consumer client to read the events that were just created (Run the command) -
  `bin/kafka-console-consumer.sh --topic <topicName> --from-beginning --bootstrap-server localhost:9092`

#### Step 6: Terminate the Kafka environment - 
- Stop the producer and consumer clients and Kafka broker server with `Ctrl+C`.
- To delete any data in the local Kafka environment including any events that have been created (Run the command) -
  `rm -rf /c/tmp/kafka-logs /c/tmp/kraft-combined-logs`

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>Grafana Loki, Grafana Alloy, Prometheus, Grafana Tempo and OpenTelemetry</u>

### Official website for Grafana - https://grafana.com/
### Official documentation link for implementing Grafana Loki using docker compose - https://grafana.com/docs/loki/latest/get-started/quick-start/

### The Docker Compose configuration runs the following components, each in its own container -
- **Flog:** which generates log lines. flog is a log generator for common log formats. ***(Here we are using our Microservices to produce logs)***
- **Grafana Alloy:** which scrapes the log lines from flog, and pushes them to Loki through the gateway.
- **Gateway (nginx):** which receives requests and redirects them to the appropriate container based on the request’s URL.
- **Loki read component:** which runs a Query Frontend and a Querier.
- **Loki write component:** which runs a Distributor and an Ingester.
- **Loki backend component:** which runs an Index Gateway, Compactor, Ruler, Bloom Planner (experimental), Bloom Builder (experimental), and Bloom Gateway (experimental).
- **Minio:** which Loki uses to store its index and chunks.
- **Grafana:** which provides visualization of the log lines captured within Loki.

### Some Explanations -
#### Loki - 
Loki with Alloy, typically doesn't directly fetch logs from the application itself. Instead, it collects logs from Docker containers through interfaces provided by Docker, 
such as logging drivers or log collection agents deployed within the Docker environment. 
These agents or logging drivers intercept logs generated by the applications running in Docker containers and then forward them to Loki for storage, indexing, and analysis. 
So, Loki indirectly receives logs from applications running in Docker containers via these Docker-provided interfaces or mechanisms.
Through the datasource configuration in grafana we get them in better UI for each microservice.
Alloy uses service discovery to automatically find containers, pods, or nodes running on a cluster. 
This is usually done using integrations with the underlying orchestration platform, such as Kubernetes, Docker etc.

#### Prometheus - 
Prometheus fetches health details from a Spring Boot application through an endpoint exposed by the Spring Boot Actuator module like /actuator/prometheus. 
We use Micrometer to make the health related data to convert it in a from which is understood by the Prometheus.
Integrates with Grafana to fetch data from Prometheus to create better GUIs.

#### Tempo and OpenTelemetry - 
Telemetry appends the microservice name, TRACE_ID and SPAN_ID to each log entry. 
Using the TRACE_ID, Grafana Tempo can trace logs across multiple microservices and visualize the complete request path for better observability.

#### Localhost Link to check prometheus metrics - http://localhost:9090/
#### Localhost Link to check Grafana tool - http://localhost:3000/
#### Default credentials for http://localhost:3000/dashboards - `User: admin, Password: admin`

---------------------------------------------------------------------------------------------------------------------------------------------------------------
## <u>TROUBLESHOOTING TECHNIQUES</u>

#### Example 1 -> 
**Error Msg -**
Failed to execute goal com.google.cloud.tools:jib-maven-plugin:3.4.2:dockerBuild (default-cli) on project cards:  
Build to Docker daemon failed, perhaps you should make sure your credentials for 
'registry-1.docker.io/library/eclipse-temurin' are set up correctly.  
See https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#what-should-i-do-when-the-registry-responds-with-unauthorized
for help: Unauthorized for registry-1.docker.io/library/eclipse-temurin: 401 Unauthorized.  
***Please Execute the command in terminal -> `docker login registry-1.docker.io`***

#### Example 2 ->
**How to check the PID of a Port and how to kill it -**  
`netstat -ano | findstr :< Port No >`    #To get the PID  of a Port  
`taskkill /PID < PID > /F`               #To kill the process

--------------------------------------------------------------------------------------------------------------------------------------

283 lecture videos as of 07/03/2025 and 17/04/2025