<h1>ReactJS-Spring-Boot-Full-Stack-App</h1>
<h3>Welcome to Mom's Sweet Shop, based on the microservices architecture, an app that simulates online ordering and delivery of confectionery.</h3>

![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/32f8fff9988007445e3f307563b6d391e04edb24/src/main/resources/static/img/main_picture.png)

<h3>Content</h3>

- Features

- Project requirements

- Getting Started

- Technologies Used

- Demo

- License

- Documentation

- More pictures


<h3>Features</h3>
<hr>
This project consists of two applications: one is a Spring Boot Rest API called pastry-shop-backend and another is a ReactJS application called pastry-shop-frontend
The app is created with a portfolio in mind and mimics an online pastry shop for ordering and delivering confectionery products.
Spring Boot Web Java backend application that exposes a REST API to manage deliveries. Its secured endpoints can just be accessed if an access token (JWT) is provided.
In the application there are user/admin roles, the processing of the requests made by the user is carried out by the administrator.

Spring-backend stores its data in a MySql database.

Spring-backend has the following endpoints

ReactJS frontend application where users can find and order products. In order to access the application, user must login using his/her username and password. All the requests coming from react-frontend to secured endpoints in spring-backend have a access token (JWT) that is generated when user / business logs in.

React-frontend uses Semantic UI React as CSS-styled framework.

<h3>Project requirements</h3>
<hr>
Created by me application is intended to serve as a portfolio for me, as although the products presented in it to be real, when ordering from the app there will be no real delivery even though it tracks the various order/delivery cycles of the product.

<h3>Getting Started</h3>
<hr>

1. Clone the repository to your local machine.

2. Configure your MySQL database by updating the application.properties file.
  
3. Configure environment variables by updating the application.properties file.
   
4. Build and run the project pastry-shop using maven

5. Build and run the project pastry-shop-web using npm. Follow these step first: 1) npm install 2) go to \pastry-shop-web 3) npm start.

6. Access the web application by visiting http://localhost:3000 in your web browser.

7. Create admin and user accounts, manage products, and place orders as needed.

Enjoy

<h3>Technologies Used</h3>
<hr>

- Java 17

- Spring Boot 3.0.2

- Spring Security

- Spring Data JPA

- Jwt.io

- Web Sockets

- Multithreading

- React, Bootstrap for React

- HTML, CSS, JavaScript#

- Responsive web design

- SockJS client and StompJS

- MySQL

- Cloudinary for managing images

- Spring Boot Mail for sending emails

- Swagger springdoc-openapi v2.2.0 

- And more...

<h3>Demo</h3>
<hr>

https://sladkarnicata-na-mama.azurewebsites.net/

<h3>License</h3>
<hr>

- MIT License

<h3>Documentation</h3>
<hr>

After start App can see documentation on this link: http://localhost:8080/swagger-ui/index.html#/

![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/7460f88a35873477f461d2ce2ea4723c68b6e3a9/src/main/resources/static/img/picture12.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/5c414de86361647c90a5ac800d3e2fb3841a4b96/src/main/resources/static/img/picture13.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/37f6bea23e2f8ea91a7636640ac994e56d6e7611/src/main/resources/static/img/picture14.png)

<h3>More pictures</h3>
<hr>

![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture1.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture2.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture3.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture5.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture6.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/baa2a7b58e17ab5fc22a57dd77b7bbcc6500d64c/src/main/resources/static/img/picture7.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/75f0929a5cb9dec466534e7268c3fe1ad5a1108f/src/main/resources/static/img/picture8.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/c33d6835367e8684f4c174d727ea108952b5e8c3/src/main/resources/static/img/picture9.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/2277a5ca9f3abb01f74b17cd91120fdfe67055c1/src/main/resources/static/img/picture10.png)
![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/85cfa53de4e0fd3584030336576d8ef5799192f3/src/main/resources/static/img/picture11.png)

