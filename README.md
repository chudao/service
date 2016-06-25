# chudao

## Getting Started

1. Start the application: `lein run`
2. Go to [localhost:8080](http://localhost:8080/) to see: `Hello World!`
3. Read your app's source code at src/chudao/service.clj. Explore the docs of functions
   that define routes and responses.
4. Run your app's tests with `lein test`. Read the tests at test/chudao/service_test.clj.
5. Learn more! See the [Links section below](#links).

## Serivce Documentation
Please change localhost:7002 to appropriate doamin (heroku) if you are not running service from local

### Home 
Endpoint: `http://localhost:7002/ GET`

### Registration
Endpoint: `http://localhost:7002/auth/register  POST`

Request Body: `{"username" : "zach", "password" : "chen"}`

Response Body: 

Happy case: `{"response-code": "010", "response-message": "registration success", "auth-token": "womenchudaola" }`

User duplcate case: `{"response-code": "011", "response-message": "registration failure: user already exists"}`

### Login
Endpoint: `http://localhost:7002/auth/login POST`

Request Body: `{"username" : "zach", "password" : "chen"}`

Response Body: 

Happy case: `{"response-code": "000", "response-message": "login success", "auth-token": "womenchudaola"}`

Bad case: `{"response-code": "001", "response-message": "login failure: user not exists or password incorrect" }`

### Photo Upload (multipart/form-data post technique)
Endpoint: `http://localhost:7002/binary/upload GET POST`

Response Body: 

Happy case: `{"response-code":"020","response-message":"upload success"}`

### Photo Download
Endpoint: `http://localhost:7002/binary/download POST`

Request Body: `{"file-name" : "file-key-in-aws-s3"}`

Response: file download stream
