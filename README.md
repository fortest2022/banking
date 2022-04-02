# Banking application

## Overview
Application implemented with usage of Spring Boot, as a database I used Postgres.
Three-tier architecture is used, api, business logic and database layers are isolated from each other.
For database layer, I chose pure sql, without any ORM framework.
I wrote some integration tests, one for each operation.
Api documentation is available through http://127.0.0.1:8080/banking/swagger-ui, you can also send test request from there.

##Project structure

com.bis.banking.config - common configurations for project.

com.bis.banking.converters - data converters between API, DB and service layers.

com.bis.banking.dto - data transfer objects.

com.bis.banking.repository - database layer.

com.bis.banking.rest - api layer.

com.bis.banking.service - business logic layer.

com.bis.banking.utils - common utils.

## How to run tests
To run integration tests you should have docker installed.
If you don't have it, just download and run docker-desktop from https://www.docker.com/get-started/, it should be enough.
Tests are located in 'src/test/java'(AccountsIntegrationTest and TransactionTest).
They will download and configure Postgres image automatically on the first run.

## How to run application
To run application you need to have Postgres already installed, or you can download and install it from https://postgresapp.com/downloads.html.
Login, password and other connection information can be configured in application.properties.
To create database and tables please use script test/resources/init_script.sql
After everything in configured please start com.bis.banking.BankingApplication

## Examples
Better to use swagger, it is located on http://127.0.0.1:8080/banking/swagger-ui/, but just in case here is some request and response examples:


###Creation of account

Request:
```json
curl -X POST "http://127.0.0.1:8080/banking/api/v1/accounts" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"currency\":\"USD\",\"ownerName\":\"Some Name\"}"
```
Response:
```json
{
  "accountNumber": "AK2071450063",
  "ownerName": "Some Name",
  "currency": "USD"
}
```
### Deletion of account(deactivation)

Request:
```json
curl -X DELETE "http://127.0.0.1:8080/banking/api/v1/accounts/AK2071450063" -H "accept: */*"
```
Response:
```json
```
### Cash deposit

Request:
```json
curl -X POST "http://127.0.0.1:8080/banking/api/v1/transactions" -H "accept: */*" -H "Content-Type: application/json" -d "{\"amount\":10000,\"currency\":\"USD\",\"targetAccountNumber\":\"BK868578765\",\"type\":\"CASH_DEPOSIT\"}"
```
Response:
```json
```
### Cash withdrawal

Request:
```json
curl -X POST "http://127.0.0.1:8080/banking/api/v1/transactions" -H "accept: */*" -H "Content-Type: application/json" -d "{\"amount\":10000,\"currency\":\"USD\",\"sourceAccountNumber\":\"BK868578765\",\"type\":\"CASH_WITHDRAWAL\"}"
```
Response:
```json
```
### Money transfer

Request:
```json
curl -X POST "http://127.0.0.1:8080/banking/api/v1/transactions" -H "accept: */*" -H "Content-Type: application/json" -d "{\"amount\":10000,\"currency\":\"USD\",\"sourceAccountNumber\":\"BK868578765\",\"targetAccountNumber\":\"AK2071450063\",\"type\":\"INTERNAL_TRANSFER\"}"```
Response:
```json
```
### International transfer

Request:
```json
curl -X POST "http://127.0.0.1:8080/banking/api/v1/transactions" -H "accept: */*" -H "Content-Type: application/json" -d "{\"amount\":1000,\"currency\":\"USD\",\"sourceAccountNumber\":\"BK868578765\",\"type\":\"EXTERNAL_TRANSFER\"}"
```
Response:
```json
```
### Getting the balance

Request:
```json
curl -X GET "http://127.0.0.1:8080/banking/api/v1/accounts/BK868578765" -H "accept: */*"
```
Response:
```json
{
  "accountNumber": "BK868578765",
  "balance": 212600,
  "currency": "USD"
}
```
###List of accounts

Request:
```json
curl -X GET "http://127.0.0.1:8080/banking/api/v1/accounts" -H "accept: */*"
```
Response:
```json
[
  {
  "accountNumber": "1112",
  "ownerName": "Captain Barbosa",
  "currency": "USD"
},
{
"accountNumber": "BK868578765",
"ownerName": "Capt Morgan",
"currency": "USD"
}
]
```

### What can be improved in real application

In 'real' life JUnit tests with good code coverage of business logic are required.
Money exchange service is needed, as currently only transfers between accounts in the same currency are allowed.
Exception handling and input validation(I've added a couple of checks, but not a proper handling).
Proper handling of concurrent transactions, security, scalability etc, etc.

