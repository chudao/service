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

User/Product id not exists case: `{"response-code":"021","response-message":"user/product id not exists"}`

### Photo Download
Endpoint: `http://localhost:7002/binary/download GET POST`

Get Request: `http://localhost:7002/binary/download?file-name=file-key-in-aws-s3`

Post Request Body: `{"file-name" : "file-key-in-aws-s3"}`

Response: file download stream

### Product Add 
Endpoint: `http://localhost:7002/product/add POST`

Post Body:
```
{
    "product-name": "abcdefg",
    "product-description": "description",
    "product-link": "link",
    "product-brand": "brand",
    "brand-link": "brandlink",
    "product-tags": "hat,skirt,male,female,unisex"
}
```

Response Bod:
```
{
    "response-code": "030",
    "response-message": "product addition success",
    "product-id": 10
}
```

### Query
Endpoint: `http://localhost:7002/query/**`

#### Query by user id
Parameterized Get endpoint: `http://localhost:7002/query/user/[userid]`

Response body: 
```
{
    "response-code": "030",
    "response-message": "query success",
    "response-data": [
        {
            "FileKey": "67d35b00-c071-4762-848e-e3dabdcc91d3---1169340_783837271749806_676198696_n.jpg",
            "ProductDescription": "qweqweqw",
            "UserId": 20,
            "FileId": 4,
            "ProductBrand": "dsadsadsa",
            "ProductCategory": "adsadsad",
            "FileName": "1169340_783837271749806_676198696_n.jpg",
            "ProductLink": "asdsadsa",
            "BrandLink": "dsadsadas",
            "ProductName": "zxcadasd"
        },
        {
        }...
    ]
 }
```
