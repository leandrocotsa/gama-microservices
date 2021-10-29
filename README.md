# Microservices-based e-commerce platform

This is the source code for a test application built for the purpose of my master's dissertation. 
This is one out of 3 different versions of the same application built for research purposes. One features a microservices-based architecture and a message-driven 
approach (this repository), other a direct synchronous inter-service communication (link), and other following a monolithic approach (link).

Several typical microservices patterns, such as CQRS, database-per-service, 
Saga Transactions, and more were implemented. This version features a message-driven approach, 
using RabbitMQ as the message broker, making the services completely unaware of each other, 
promoting fault isolation and loose coupling.


## Stacks

The Docker Swarm cluster is composed of 4 stacks:
- Main application stack, composed of:
  - Inventory Serivce
  - Order Service 
  - Payment Service
  - Product Service
  - Payment Service
  - Promotion Service
  - Review Service
  - Shopping-cart Service
  - User Service
  - Catalog View
  - Orders/Users View
  - API Gateway
  - RabbitMQ
  - MySQL database
  - MongoDB database
  
The main business services were built using Java and Spring Boot version `2.4.4`.

- Monitoring stack
  - Grafana
  - cAdvisor
  - Prometheus
  - Node exporter
  
- Logging & Tracing stack
  - Elastic Search
  - Logstash
  - Kibana
  - Zipkin
  
## Architecture

img here

## Requirements

This platform was built for a Docker Swarm cluster of Raspberry pi 4, which features a special type of processor architecture. 
This means that the built docker images (available in Docker Hub) are not compatible with the typical AMD64 architectures. 
In the context of a cluster of Raspberry Pi, once a swarm cluster is created and this repository is cloned run these commands inside this repository's
folder on the master node to deploy and initialize each of the stacks:

```
docker stack deploy --compose-file main-app-compose.yml gama
docker stack deploy --compose-file monitoring-compose.yml monitoring
docker stack deploy --compose-file tracing-logging-compose.yml logging
```

In case you want to use your own database settings change the `src/main/resources/application.properties` file in each service and then build it with:

```
mvn clean package -DskipTests  
docker build -t <image-name>
```
The `<image-name>` should replace the image name present in the `main-app-compose.yml` of the corresponding service. Then you can deploy the main application stack 
using `docker stack deploy --compose-file main-app-compose.yml gama`.


