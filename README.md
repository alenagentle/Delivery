# Delivery service


## Run it


* Run via Docker Compose <br>
  `docker-compose up`



## Check it with Swagger

Go to [Swagger page](http://localhost:8080/swagger-ui/index.html#/)


## Sample APIS

##### 1. API to get JWT Auth Token
```
curl --location --request POST 'localhost:8080/authentication/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "user01@mail.ru",
    "password": "aA1234%"
}'
```

* Simple response with JWT
```
{"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDFAbWFpbC5ydSIsImlzcyI6ImRlbGl2ZXJ5IiwiZXhwIjoxNjc5MTM1NjQ4LCJpYXQiOjE2NzkxMzQ0NDh9.Rlo9hvc0RTwkbQxSixo6vA_bv3i2NBoF8jHbrXzsKYc","refreshToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDFAbWFpbC5ydSIsImlzcyI6ImRlbGl2ZXJ5IiwiZXhwIjoxNjc5MTM1NjQ4LCJpYXQiOjE2NzkxMzQ0NDh9.Rlo9hvc0RTwkbQxSixo6vA_bv3i2NBoF8jHbrXzsKYc"}
```

## Credentials for DB connection
Username|Password|DB|port
--------|--------|------|----
postgres|123456|delivery|5434