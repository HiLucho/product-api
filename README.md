# PRODUCT API
____________________

This is a service to persist and retrieve products.

Any question or comment write me on slack or by email at __l.avila@globant.com__

##  Getting Started
____________________

### Parameters

To run this APP properly run correctly, the following properties must be configured.

For dev and local environment, simply you can use the default values on the specific property file.
The files are in on __src/main/resources__ and the name is application-{environment}.properties

You can run locally using docker for a mySql instance executing:

```
docker run --name=mysql1 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
```

Then, you must configure the properties like:

```
spring.datasource.username=root
spring.datasource.password=your-secret-pw
```

### Run test with coverage

* ```./gradlew test jacocoTestReport```

### Run clean

* ```./gradlew clean```

### Run app locally

* ```./gradlew bootRun```

### Utils Url (local example)

#### To POST a product
```
curl --location --request POST 'localhost:8080/api/v1/product' \
--header 'Content-Type: application/json' \
--data-raw '{
    "sku": "AAA-1000010",
    "name":"nametxt",
    "brand":"brandtxt",
    "size": "sizetxt",
    "price": 1299.00,
    "principalImage": "http://somedata.com"    
}' 
```
#### To GET a list of all products
```
curl --location --request GET 'localhost:8080/api/v1/product'
```

#### To GET a product by sku
```
curl --location --request GET 'localhost:8080/api/v1/product/AAA-1000010'
```

#### To DELETE a product by sku
```
curl --location --request DELETE 'localhost:8080/api/v1/product/AAA-1000010'
```

#### To PUT a product by sku
```
curl --location --request PUT 'localhost:8080/api/v1/product/AAA-1000010' \
--header 'Content-Type: application/json' \
--data-raw '{

    "name":"otroName",
    "brand":"otroBrand",
    "size": "400202",
    "price": 433222.00,
    "principalImage": "http://somedata.com",
    "otherImages": [
        {"otherImage": "http://somedata3.com"}
    ]
}'
```