# heycar Challenge
Set of REST APIs to upload and search vehicle listings. The application is built using Gradle, Spring Boot, Docker and H2.

## Getting Started

- To get started locally with Spring Boot:

./gradlew bootRun

- To build a Docker container:

docker build -t jsallari/heycar .

## API
It is possible to check the API definition via Swagger UI: http://localhost:8080/swagger-ui/index.html.

Some decisions I made:
- I included an endpoint to create a Dealer. This is mainly to keep the right data in place when creating new listings. In order to create new listings, it is necessary to have an existing dealer id.

``curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"name":"dealerName"}' \                                                                                               
    http://localhost:8080/dealers
``

## How to start testing the app

- Create listings

``curl --header "Content-Type: application/json" \
--request POST \
--data '[{"code":"code1", "model":"model1", "make":"make1", "year":"2021", "kw":"10000", "color":"color1", "price":"2000"},{"code":"code2", "model":"model2", "make":"make2", "year":"2022", "kw":"10002", "color":"color2", "price":"2002"},{"code":"code3", "model":"model3", "make":"make3", "year":"2023", "kw":"10003", "color":"color3", "price":"2003"}]' \
http://localhost:8080/dealers/bb91b031-058c-453d-b79d-1f59f328e0be/vehicle_listings``

- Search all listings 

``curl http://localhost:8080/search``

- Search all listings with some color

``curl http://localhost:8080/search?color=color1``

## Design Decisions

### Data Management
The application is using H2 for persistency.

Some decisions made:
- Every object has its own ID (UUID). A base class **HeyCarEntity** is provided for basic attributes.
- Every object is immutable. For this reason, if needed the services are building new objects from the requests. This would keep the Java API safer.
- For the bulk upsert, the **ListingService** consider every record insert independently, providing in the response a list for the ones that failed. 

### Logging, error and Exception Handling

- Every API error response is built into an object containing **message** and **status**. 
- Slf4j is used to track every interaction with the app.

### Challenges

- **Bulk upsert**: it was decided to bring all the records from the dealer into memory and then check one by one if it exists (**ListingService.uploadVehicles**). Although this might not be good for higher data loads in terms of memory consumption, it was chosen instead of querying the DB for each record by ID.

## To implement with more time

- Monitoring: adding Prometheus and sending some metrics for the upload (number of records sent, number of failures).
- Authentication and authorization.   
- Bulk upsert async: implementing a job API, that can create a job with the records to sent and can be polled for the results. This could help with higher number of requests.
- Implementing a cache would help with the bulk upsert case.
- CI/CD pipeline.
