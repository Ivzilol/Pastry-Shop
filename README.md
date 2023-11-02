<h1>ReactJS-Spring-Boot-Full-Stack-App</h1>

This project consists of two applications: one is a Spring Boot Rest API called pastry-shop-backend and another is a ReactJS application called pastry-shop-frontend
The app is created with a portfolio in mind and mimics an online pastry shop for ordering and delivering confectionery products.


<h3>Applications</h3>

- spring-backend

Spring Boot Web Java backend application that exposes a REST API to manage deliveries. Its secured endpoints can just be accessed if an access token (JWT) is provided.
In the application there are user/admin roles, the processing of the requests made by the user is carried out by the administrator.

spring-backend stores its data in a MySql database.

spring-backend has the following endpoints

- react-frontend

ReactJS frontend application where users can find and order products. In order to access the application, user must login using his/her username and password. All the requests coming from react-frontend to secured endpoints in spring-backend have a access token (JWT) that is generated when user / business logs in.

react-frontend uses Semantic UI React as CSS-styled framework.

- backend
  
-- Java 17

-- JWT

-- WebSockets

- frontend

-- React

-- NodeJs.v.18

-- sockjs
