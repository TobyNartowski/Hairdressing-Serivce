# Hairdressing-Serivce
Web application to manage hairdressing services

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## General info
The design of the service store was created as a web application,made in java. Allows you to create new users,
creating reservations, viewing order history and much more. It is a fully functional system of salon management and supervision
hairdressing.

## Technologies
* Apache maven version 3.0 
* Java EE version 8
* MySQL version .0.15
* Hibernate ersion 4.3.2 
* Jax-rs version 3.0.9
* Angular JS version 1.7.8

## Setup
To run this project, install it locally using maven:
```
$ mvn package
$ cd target
$ java -jar <projectName>
```

## Features
The application was divided into 3 users: client, employee and admin.

In the admin panel you can add new services, register new employees and add new products that will be used in services: such as shampoo or conditioner.

Employee panel on the home page has a table that displays today's bookings for a logged employee. If a given employee does not have a reservation, he will receive an appropriate message.

The user has the following functions: creation of a new account and login, creation of new bookings, modification of bookings - changes of date, services, cancellation of bookings.
View pending reservations and history, browse pending reservations and reviewing the opinions of his own and other clients.

![](src/main/webapp/img/unknown.png)

## Authors
* Piotr Piasecki - [Vattier56](https://github.com/Vattier56)
* Tobiasz Nartowski - [TobyNartowski](https://github.com/TobyNartowski)
* Michał Młodawski - [SimpleMethod](https://github.com/SimpleMethod)
    


