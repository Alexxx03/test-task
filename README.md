# oleksandr.danylin test project

## uncommon libraries 

    1) io.jsonwebtoken:jjwt-api - for proccessing jwt;
    2) org.modelmapper:modelmapper - makes its easy to map DTO to model and otherwise
    3) org.projectlombok:lombok - to reduce boilerplate code;

## build and run application

    1) Install Intelij Idea, Java(if not installed), MySQL;
    2) Create database on your local computer;
    3) Edit run configurations, add env variables like DB_URI, DB_USERNAME, DB_PASSWORD;
    4) Run app;

## predefined users credentials

    1) Admin : { "username" : "superAdmin", "password" : "superAdmin" };
    2) User :{ "username" : "superUser", "password" : "superUser" };