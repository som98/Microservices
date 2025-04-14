# Microservices

### Docker Run Command for MySQL DB ->
#### Example ->
###### docker run -p 3306:3306 --name accountsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsDB -d mysql:latest 
###### docker run -p 3307:3306 --name cardsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsDB -d mysql:latest
###### docker run -p 3308:3306 --name loansDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansDB -d mysql:latest

### Docker Run command for RabbitMQ ->   
#### Example ->
###### docker run -dit --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management

### Docker Run command for Redis ->
#### Example ->
###### docker run -p 6379:6379 --name redis -d redis:latest

### Docker Run command for Keycloak ->
#### Example ->
###### docker run -p 7080:8080 -d -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev
#### Website link - https://www.keycloak.org/getting-started/getting-started-docker
#### Keycloak Admin Console - http://localhost:7080/admin

### Docker command for pushing images into Docker Hub ->
#### Example ->
###### docker image push docker.io/somshubhraroy/accounts:v4

--------------------------------------------------------------------------------------------------------------------------------------
Adminer UI for MySQL ->  
docker run -p 1000:8080 --link accountsDB:db -d adminer  
docker run -p 2000:8080 --link cardsDB:db -d adminer  
docker run -p 3000:8080 --link loans:db -d adminer
--------------------------------------------------------------------------------------------------------------------------------------

## TROUBLESHOOTING TECHNIQUES ->

#### ERROR Example 1 ->  
Failed to execute goal com.google.cloud.tools:jib-maven-plugin:3.4.2:dockerBuild (default-cli) on project cards:
Build to Docker daemon failed, perhaps you should make sure your credentials for 
'registry-1.docker.io/library/eclipse-temurin' are set up correctly.
See https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#what-should-i-do-when-the-registry-responds-with-unauthorized
for help: Unauthorized for registry-1.docker.io/library/eclipse-temurin: 401 Unauthorized.
***Execute the below command in terminal->***
###### docker login registry-1.docker.io

--------------------------------------------------------------------------------------------------------------------------------------

### How to check the PID of a Port and how to kill it ->
**netstat -ano | findstr :< Port No >**    #To check the PID  of a Port  
**taskkill /PID < PID > /F**               #To kill the process


--------------------------------------------------------------------------------------------------------------------------------------

283 lecture videos as of 07/03/2025