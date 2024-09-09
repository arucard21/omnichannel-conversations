# Omni-channel Conversations backend

This is a proof-of-concept implementation of a REST API using the Microprofile specifications.

This is tested using an Apache TomEE 9.x server, which contains Apache Tomcat 10.x and supports Jakarta EE 9.1 and Microprofile 5.0.

## Requirements
You must add the `tomee.mp.scan = all` property to TomEE's system.properties file to ensure that it scans for all Microprofile libraries.

To run the application at the root, this must be configured in TomEE. You can either use `ROOT.war` as the name of the war file (which is configured as such in this project through Gradle). 
When running from your IDE, you can run the project on the server. Once it's added to the server, you can change its path. If you provide an empty path, it will be running on the root. You need to restart the server to apply this setting.

## Implemented Microprofile specifications
This lists the Microprofile specifications that have been implemented in this project. It mainly shows how you can trigger them to see how they work.

### Jakarta EE 9.1 (JAX-RS 3.0)
`http://localhost:8080/hello`  
Shows a basic JAX-RS web resource that return plain text.

`http://localhost:8080/visits` 
Shows a proper JAX-RS web resource, though limited to showing a list of a few hard-coded API resources.

### OpenAPI 3.0
`http://localhost:8080/openapi`  
Returns YAML by default, set Accept header to application/json for JSON response.

`http://localhost:8080/openapi?format=json`  
This is an alternative approach to get a JSON response.

### Metrics 4.0
`http://localhost:8080/metrics`  
Shows metrics in Prometheus format.

### Health 4.0
`http://localhost:8080/health`  
`http://localhost:8080/health/live`  
`http://localhost:8080/health/ready`  
`http://localhost:8080/health/started`  
Shows the health checks that are implemented in the project.

### Fault Tolerance 4.0
`http://localhost:8080/hello/timeout`  
This API request will always timeout so it shows how the fallback mechanism is triggered.

### Config 3.0
`http://localhost:8080/hello/config-property`  
This API request returns a property defined in the `microprofile-config.properties` file in `src/main/resources`. 

### Rest Client 3.0
`http://localhost:8080/hello/random-joke`  
This API request uses a client to access the random joke API at https://official-joke-api.appspot.com/random_joke and returns the setup and punchline of the joke.

### JWT RBAC 2.0
`http://localhost:8080/hello/authenticated`  
This API request returns a plain text message but it requires valid JWT-based authentication for a user with the role `admin`. 

You can include the following as the value of the `Authorization` header in the API request (make sure to trim any spaces at the beginning or end):

```
Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNjgzMjA0MTU2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0LyIsImV4cCI6NDEwMjQ0NDc5OSwiZ3JvdXBzIjpbImFkbWluIl19.F_3s0Jw4RlUt4w6U_8i53J2C7wRBoze48LRsNVMFKFF2cbrXKH_Nq_qjl0IgQUEtT1WbctLS5d6HxeXYT4cysmlYgeRxXrUpe1myNN0mkMKc5EaT1oTgWoVlhrSiNe30ZXC9np3ptObrVYuTplqS8BoL_3iRauIYxzcb-5ZM2sbDUnidmkldNfRvcElmEQmyb-aa0IIhFAkemwZ-1ZX-bBnk10bpYuEWmOhBZCRuSD1fASYwslCggrcVSomlbJG_eQUAwJAV7w6DNkWXAct7VKslMwaHzuxh6DOYPY1onk911gD8b8IJCooQrMSNuvgovR8dxyUs67wy2GUCM-YjFQ
```

This token contains the minimum headers and claims required by Microprofile JWT-RBAC and is valid until 2099-12-31T23:59:59.000Z. It contains only `admin` in its `groups` claim which Microprofile automatically maps to a corresponding `admin` role. 

You can also [modify this JWT token](https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNjgzMjA0MTU2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0LyIsImV4cCI6NDEwMjQ0NDc5OSwiZ3JvdXBzIjpbImFkbWluIl19.F_3s0Jw4RlUt4w6U_8i53J2C7wRBoze48LRsNVMFKFF2cbrXKH_Nq_qjl0IgQUEtT1WbctLS5d6HxeXYT4cysmlYgeRxXrUpe1myNN0mkMKc5EaT1oTgWoVlhrSiNe30ZXC9np3ptObrVYuTplqS8BoL_3iRauIYxzcb-5ZM2sbDUnidmkldNfRvcElmEQmyb-aa0IIhFAkemwZ-1ZX-bBnk10bpYuEWmOhBZCRuSD1fASYwslCggrcVSomlbJG_eQUAwJAV7w6DNkWXAct7VKslMwaHzuxh6DOYPY1onk911gD8b8IJCooQrMSNuvgovR8dxyUs67wy2GUCM-YjFQ&publicKey=-----BEGIN%20PUBLIC%20KEY-----%0AMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu1SU1LfVLPHCozMxH2Mo%0A4lgOEePzNm0tRgeLezV6ffAt0gunVTLw7onLRnrq0%2FIzW7yWR7QkrmBL7jTKEn5u%0A%2BqKhbwKfBstIs%2BbMY2Zkp18gnTxKLxoS2tFczGkPLPgizskuemMghRniWaoLcyeh%0Akd3qqGElvW%2FVDL5AaWTg0nLVkjRo9z%2B40RQzuVaE8AkAFmxZzow3x%2BVJYKdjykkJ%0A0iT9wCS0DRTXu269V264Vf%2F3jvredZiKRkgwlL9xNAwxXFg0x%2FXFw005UWVRIkdg%0AcKWTjpBP2dPwVZ4WWC%2B9aGVd%2BGyn1o0CLelf4rEjGoXbAAEgAqeGUxrcIlbjXfbc%0AmwIDAQAB%0A-----END%20PUBLIC%20KEY-----) if you want. For example, you can update the `exp` field with a date [formatted in milliseconds since the epoch](https://www.epochconverter.com/) that has already passed to make the authentication fail. Or you can add or remove roles in the `groups` claim.

### OpenTracing 3.0
This has been replaced with Open Telemetry 1.0 in Microprofile 6.0. These specifications are not compatible and no migration path has been defined for them. So it doesn't make sense to try to get OpenTracing working when it is already deprecated in favor of Open Telemetry. Open Telemetry should also work automatically with JAX-RS so no configuration should be needed, aside from possibly enabling it.

## Deployment

The REST API is packaged as a WAR file with Gradle and can be deployed as a Docker image. A `docker-compose.yml` file is provided to automatically build the Docker image, as defined in `Dockerfile`, then start the server with the REST API.

You can start the server with these commands.

```shell
./gradlew build
docker-compose up -d --build
```

If you want to build the Docker image before starting the server, you can do so with `docker-compose build`
You can also use `Dockerfile` to build an image directly with `docker build .`. You should then also tag it as needed with the `-t` parameter. 

## Automated tests

You can run the unit test with the standard `./gradlew test` command. The main portion of automated testing is done through the integration tests which can be run with `./gradlew integrationTest`.

The integration tests download, install and configure the Apache TomEE server, install the API on it, and run the tests against the API running on that server. 

To run the integration tests in the IDE, you need to ensure that the following JVM arguments are specified:

```
-Dtomee.classifier=plus
-Dtomee.properties="tomee.mp.scan=all"
```

These JVM arguments configure the server against which the automated tests run. The classifier ensures that the "plus" version of  TomEE is used, which supports the Microprofile standards as well as all Jakarta EE standards. The second argument adds a property to the server that enables all Microprofile implementations, without which the API will not start on the server.
