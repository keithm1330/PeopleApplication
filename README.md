# PeopleApplication

A Simple Application to manage people and addresses

## Description
The application is split into two parts a spring boot application as a REST service that incorporates  hibernate framework and embedded h2 as the database & a simple react dynamic app for the ui

```bash
Clone the application 'git clone https://github.com/keithm1330/PeopleApplication.git'
```

## Rest Service

To start the service  
cd into the people folder and run ```
mvn spring-boot:run```  
Service port can be configured in the application.properties file  
To run the tests ```mvn clean install```  
curl --location --request GET 'localhost:8080/api/people'

## React UI
** React must be installed on machine To Run the React UI  
CD into the uiapp folder run 'yarn start'  
App should be running on localhost:3000