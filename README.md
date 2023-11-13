<h1>ReactJS-Spring-Boot-Full-Stack-App</h1>
<h3>Welcome to Mom's Sweet Shop, an app that simulates online ordering and delivery of confectionery.</h3>

![Примерна снимка](https://github.com/Ivzilol/pastry-shop/blob/32f8fff9988007445e3f307563b6d391e04edb24/src/main/resources/static/img/main_picture.png)

<h3>Content</h3>

- Features

- Project requirements

- Getting Started

- Technologies Used

- Demo

- License

- More pictures


<h3>Features</h3>
<hr>
This project consists of two applications: one is a Spring Boot Rest API called pastry-shop-backend and another is a ReactJS application called pastry-shop-frontend
The app is created with a portfolio in mind and mimics an online pastry shop for ordering and delivering confectionery products.
Spring Boot Web Java backend application that exposes a REST API to manage deliveries. Its secured endpoints can just be accessed if an access token (JWT) is provided.
In the application there are user/admin roles, the processing of the requests made by the user is carried out by the administrator.

spring-backend stores its data in a MySql database.

spring-backend has the following endpoints

ReactJS frontend application where users can find and order products. In order to access the application, user must login using his/her username and password. All the requests coming from react-frontend to secured endpoints in spring-backend have a access token (JWT) that is generated when user / business logs in.

react-frontend uses Semantic UI React as CSS-styled framework.

<h3>Project requirements</h3>
<hr>
Created by me application is intended to serve as a portfolio for me, as although the products presented in it to be real, when ordering from the app there will be no real delivery even though it tracks the various order/delivery cycles of the product.

<h3>Getting Started</h3>
<hr>
1. Clone the repository to your local machine.

2. Configure your MySQL database by updating the application.properties file.
3. 
4. Configure environment variables by updating the application.properties file.
5. 
Build and run the project pastry-shop using maven

Build and run the project pastry-shop-web using npm. Follow these step first: 1) npm install 2) go to \boxerclub-bg-rest-client 3) npm start.

Access the web application by visiting http://localhost:3000 in your web browser.

Create admin and user accounts, manage products, and place orders as needed.

Enjoy

