# Reactive Java Server

using **Spring**, **reactive mongodb**, **swagger**

# How to run the server

## Before running the server

### Gradle
refresh the gradle in order to download the external packages

### MongoDB
run the mongodb service

    mongod

## Run the server
The main server is locate in the Application.java file
The server is run on port 8095 (you can change it in the application.properties file)

Go to [http://localhost:8095/swagger-ui.html](http://localhost:8095/swagger-ui.html) using swagger UI

If you want to connect to non-local mongodb instance all you need to the url to application.properties under **spring.data.mongodb.uri** filed
