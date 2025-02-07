# Microservices

### Docker Run Command for MySQL DB ->
#### Example ->
docker run -p 3306:3306 --name accountsDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsDB -d mysql:latest

### Docker Run command for RabbitMQ ->   
#### Example ->
docker run -dit --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management
