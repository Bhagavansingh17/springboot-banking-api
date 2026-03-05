# Banking API (Spring Boot)

A REST API for banking operations built with Spring Boot.

## Features
- Create Account
- Deposit Money
- Withdraw Money
- Transfer Money
- Transaction History
- Pagination Support
- Swagger API Documentation

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger

## Run Project
Open browser:

http://localhost:8080/swagger-ui/index.html

## Example API

Create Account:

POST /accounts

{
"holderName": "Singh",
"initialBalance": 500,
"pin": "1234"
}