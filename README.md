# chudao

## Getting Started

1. Start the application: `lein run`
2. Go to [localhost:8080](http://localhost:8080/) to see: `Hello World!`
3. Read your app's source code at src/chudao/service.clj. Explore the docs of functions
   that define routes and responses.
4. Run your app's tests with `lein test`. Read the tests at test/chudao/service_test.clj.
5. Learn more! See the [Links section below](#links).

## Authentication Documentation
All paths except the ones under `/auth/` are secured. Upon successful registration or login, an header X-Auth-Token:xxxxx will be returned.
All requests to secured paths will need to include X-Auth-Token key value to perform server side validation, otherwise a 401 Unathorized response
will be returned.

## Serivce Documentation
Please change localhost:7002 to appropriate doamin (heroku) if you are not running service from local

### Home 
Endpoint: `http://localhost:7002/ GET`

### Registration
Endpoint: `http://localhost:7002/auth/register  POST`

Request Body: 
```
{
    "user-name" : "newwwuwser", 
    "user-category": "user" / "stylist",
    "password" : "chen"
}
```

Response Body: 

Happy case: 
```
{
    "response-code": "010",
    "response-message": "registration success",
    "user-id": 135,
    "user-name": "newwwuser"
}
```

User duplcate case: `{"response-code": "011", "response-message": "registration failure: user already exists"}`

### Login
Endpoint: `http://localhost:7002/auth/login POST`

Request Body: `{"user-name" : "zach", "password" : "chen"}`

Response Body: 

Happy case: 
```
{
    "response-code": "000",
    "response-message": "login success",
    "user-id": 96,
    "user-name": "encrypteduser",
    "user-cateogry": "stylist"
}
```

Bad case: `{"response-code": "001", "response-message": "login failure: user not exists or password incorrect" }`

### Photo Upload (multipart/form-data post technique)
Endpoint: `http://localhost:7002/binary/upload GET POST`

Response Body: 

Happy case: `{"response-code":"020","response-message":"upload success","file-key":"b3c2c700-2f5d-41b8-8bbd-688fbc0665ce---12934865_1134037719950239_248101953_n.jpg"}`

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

#### Query file upload by user id
Endpoint: `http://localhost:7002/query/file/user-id`

Post body:
```
{
    "user-id": 20
}
```

Response:
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": [
        {
            "FileId": 25,
            "UserId": 20,
            "ProductId": 3,
            "FileName": "11324418_1476714922648963_15264649_n.jpg",
            "FileKey": "fa7525f0-ce14-438f-9943-510d1f83605e---11324418_1476714922648963_15264649_n.jpg"
        },
        {
            "FileId": 26,
            "UserId": 20,
            "ProductId": 4,
            "FileName": "12230810_1630318477220878_659529415_n.jpg",
            "FileKey": "75ca918a-f421-442b-b8a6-b72d3287219e---12230810_1630318477220878_659529415_n.jpg"
        }
    ]
}
```
#### Query by tags
Endpoint: `http://localhost:7002/query/product/tags POST`

Post Body:
```
{
    "product-tags": "male,female,unisex"
}
```

Response:
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": {
        "ProductIds": [9, 15, 16]
    }
}
```

#### Query by product ids
Endpoint: `http://localhost:7002/query/product/ids`

Post Body:
```
{
    "product-ids": "9,15,16"
}
```

Response Body:
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": [
        {
            "ProductId": 9,
            "ProductName": "blabla",
            "ProductBrand": "sadsa",
            "ProductDescription": "dqweq",
            "ProductLink": "qewqd",
            "BrandLink": "qweqweqw"
        },
        {
            "ProductId": 15,
            "ProductName": "abcdefg",
            "ProductBrand": "brand",
            "ProductDescription": "description",
            "ProductLink": "link",
            "BrandLink": "brandlink"
        },
        {
            "ProductId": 16,
            "ProductName": "zxcadasd",
            "ProductBrand": "asd",
            "ProductDescription": "description",
            "ProductLink": "qweqweqw",
            "BrandLink": "asdsadsa"
        }
    ]
}
```

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

#### Query file info by product ids
Endpoint: `http://localhost:7002/query/file/product-ids POST`

Post Body:
```
{
    "product-ids": "9,15"
}
```

Response Body:
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": [
        {
            "FileId": 30,
            "UserId": 20,
            "FileName": "15016905746_a2bf2a3a41_k.jpg",
            "FileKey": "49c9a3de-a46a-44a8-b319-6f5b6862f59f---15016905746_a2bf2a3a41_k.jpg",
            "ProductId": 9
        },
        {
            "FileId": 42,
            "UserId": 20,
            "FileName": "12934865_1134037719950239_248101953_n.jpg",
            "FileKey": "c479d9b9-d8a0-4a7d-9b3d-97bf07eaf292---12934865_1134037719950239_248101953_n.jpg",
            "ProductId": 9
        }
    ]
}
```

#### Query user requests
Endpoint: `http://localhost:7002/query/request/user-id GET`

Response: 
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": [
        {
            "user-id": 145,
            "user-message": "I want shirt",
            "file-key": "blblablablab",
            "budget": "$40",
            "product-tags": "cloth,shirt",
            "request-id": "579a030762fe7e21ea8d282b"
        },
        {
            "user-id": 145,
            "user-message": "I want shirt",
            "file-key": "blblablablab",
            "budget": "$40",
            "product-tags": "cloth,shirt",
            "request-id": "579a030662fe7e21ea8d2828"
        }
    ]
}
```

#### Query User Requests by Handling Status
Endpoint: `http://localhost:7002/query/request/user/status POST`

Request Body:
```
{"status": "unresponded" / "responded"}  
```

Note: status thats not "unresponded" or "responded" will be ignore and get back all user sent requests

Response:
```
{
    "response-code": "040",
    "response-message": "query success",
    "response-data": [
        {
            "user-id": 145,
            "user-message": "I want shirt",
            "file-key": "blblablablab",
            "budget": "$40",
            "product-tags": "cloth,shirt",
            "request-id": "579b49f962fe7e31a6d47345",
            "status": "unresponded"
        },
        {
            "user-id": 145,
            "user-message": "I want shirt",
            "file-key": "blblablablab",
            "budget": "$40",
            "product-tags": "cloth,shirt",
            "request-id": "579b49f762fe7e31a6d47342",
            "status": "unresponded"
        }
    ]
}
```

### User send requests
Endpoint: `http://localhost:7002/request/add`

Post body:
```
{
    "user-id": 20,
    "user-message": "I want shirt",
    "file-key": "blblablablab",
    "budget": "$40",
    "product-tags": "cloth,shirt"
}
```

Response body:
```
{
    "response-code": "050",
    "response-message": "request addition success",
    "request-id": "57804f6d75e7ff62a9ed721c"
}
```


### Stylist Handle Request
Endpoint: `http://localhost:7002/request/handle`

Post Body:
```
{
    "request-id": "5782c02996c18100e1953a92",
    "product-ids": [20, 13]
}
```

Response Body:
```
{
    "response-code": "060",
    "response-message": "request handled success",
    "confirmation-id": "acd8d878-8657-4c89-b43e-c465182b07bf"
}
```
