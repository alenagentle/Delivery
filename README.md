# Delivery service


## Run it
* Run via Docker Compose <br>
  `docker-compose up`

## Check it with Swagger
Go to [Swagger page](http://localhost:8091/swagger-ui/index.html#/)


## Sample APIS
##### 1. Create simple user:
```
curl --location --request POST 'localhost:8091/authentication/sign-up' \
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

##### 2. Create admin:
```
curl --location --request POST 'localhost:8091/authentication/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@mail.ru",
    "password": "aA1234%"
}'
```

##### 3. Set ROLE_ADMIN for created admin:
```
/// password = 123456
psql -d delivery -U postgres -h localhost -p 5434 
```

```
INSERT INTO user_role (user_id, role_id) values ((SELECT id FROM user_data WHERE email='admin@mail.ru'), (SELECT id from role WHERE name = 'ROLE_ADMIN'));
```

##### Check the rest endpoints with swagger according to user story

## Credentials for DB connection into docker container
Username|Password|DB|port
--------|--------|------|----
postgres|123456|delivery|5434