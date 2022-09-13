## URL checker service

URL Checker service main goal is to check URLs availability and save results. URLs list, check results and check settings (check repeat interval in seconds) can be managed by means of API

Default URL availability check repeat interval is 20 seconds.

## Technology stack

* Kotlin
* Spring Boot WebFlux
* H2 DB
* Flyway
* Quartz (Job Scheduling)

## Pre-requisites

* JDK 17
* Docker

## Building

### Testing

`./gradlew test`

### Building (no tests)

`./gradlew assemble`

### Building (with tests)

`./gradlew build`

### Running in Docker

`./gradlew assemble docker dockerRun`

### Stopping Docker container

`./gradlew dockerStop`

## Using API

There is [Swagger UI for API description](http://localhost:8080/url-checker/swagger-ui.html)

### URLs Management
* get all URLs - GET [/urls](http://localhost:8080/url-checker/api/v1/urls?page=0&size=1) to get a list of all urls with pagination support
* add URL - POST String to [/urls](http://localhost:8080/url-checker/api/v1/urls) to add URL to the checklist
* delete URL - DELETE [/urls/${url}](http://localhost:8080/url-checker/api/v1/urls/https://test-url.com) to delete URL from checklist

### Check results Management
* get all check results - GET [/checkResults](http://localhost:8080/url-checker/api/v1/checkResults?page=0&size=1) to get a list of all check results with pagination support

### Service configuration Management
* change check repeat interval - POST Integer to [/configurations/repeatInterval](http://localhost:8080/url-checker/api/v1/configurations/repeatInterval/30) to update check repeat interval in seconds

## Need further support?
Contact me if you need help at ppnati55@mail.ru