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
** Run on commandline not powershell  
** May need to run  npm install react-scripts
CD into the uiapp folder run 'yarn add bootstrap@4.1.3 react-cookie@3.0.4 react-router-dom@4.3.1 reactstrap@6.5.0'  
then run 'yarn start'
App should be running on localhost:3000