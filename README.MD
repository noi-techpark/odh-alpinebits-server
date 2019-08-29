![Logo of the project](https://raw.githubusercontent.com/idm-suedtirol/odh-alpinebits/master/alpine_bits_rgb.png)

# Open Data Hub - AlpineBits Server

[Open data hub (ODH)](https://opendatahub.io/) is a platform that integrates
open source projects into a practical solution.

[AlpineBits](https://www.alpinebits.org/) is an open standard to exchange
touristic data.

This repository provides an AlpineBits server that uses the ODH tourism data
as a backend.

## Table of Contents

- [Getting started](#getting-started)
- [Running the tests](#running-the-tests)
- [Deployment](#deployment)
- [Docker environment](#docker-environment)
- [Information](#information)

## Getting started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes. Take a look
at the [Deployment](#deployment) section for notes on how to deploy
the project on a live system.

### Prerequisites

To build the project, the following prerequisites must be met:

- Java JDK 1.8 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- [Maven](https://maven.apache.org/) 3.x

For a ready to use Docker environment with all prerequisites already installed and prepared, you can check out the [Docker environment](#docker-environment) section.

> Hint: this project relies on the [AlpineBits library](https://github.com/noi-techpark/odh-alpinebits).
We are currently in the process of releasing that library to the Maven central repository.
Until then, you have to build the necessary AlpineBits library on your own. Please take a
look at the corresponding [README.me](https://github.com/noi-techpark/odh-alpinebits/blob/master/README.MD) file.

### Installing

Get a copy of the repository, e.g. by cloning it from the following location:

```bash
https://github.com/noi-techpark/odh-alpinebits-server
```

Change directory:

```bash
cd odh-alpinebits-server/
```

Build the project:

```bash
mvn clean install
```

## Running the tests

The code in this repository provides unit and integration tests.

The unit tests can be run with the following command:

```bash
mvn clean verify
```

To run the unit and integration tests, use the following command, activating the
maven `it` profile:

```bash
mvn clean verify -P it
```

You can also integrate [sonarqube](https://www.sonarqube.org/) to check the code
quality. Run the following command:

```bash
mvn clean verify sonar:sonar -P it,report
```
 
## Deployment

### WAR

The project provides an AlpineBits server in module `application-war`. If you want,
to use it, the following additional prerequisites must be met:

- Servlet Container >= 3.1 (e.g. [Tomcat 8.5](https://tomcat.apache.org/download-80.cgi))

The generated WAR file can be deployed to the servlet container.

### Spring

The project provides an AlpineBits server as Spring application in module
`application-spring`. The generated JAR file is a complete AlpineBits Server with no
further dependencies. To start it, just run the JAR file with the correct main
class `it.bz.opendatahub.alpinebitsserver.application.spring.Application`.

Assuming you are in the projects root folder, run the following command: 

```bash
java -jar application-spring/target/application-spring-0.3.0.jar it.bz.opendatahub.alpinebitsserver.application.spring.Application 
```

## Docker environment

For the project a Docker environment is already prepared and ready to use with all necessary prerequisites.

These Docker containers are the same as used by the continuous integration servers.

### Installation

Install [Docker](https://docs.docker.com/install/) (with Docker Compose) locally on your machine.

### Start and stop the containers

Before start working you have to start the Docker containers:

```
docker-compose up --build --detach
```

After finished working you can stop the Docker containers:

```
docker-compose stop
```

### Running commands inside the container

When the containers are running, you can execute any command inside the environment. Just replace the dots `...` in the following example with the command you wish to execute:

```bash
docker-compose exec java /bin/bash -c "..."
```

Some examples are:

```bash
docker-compose exec java /bin/bash -c "mvn clean install"

# or

docker-compose exec java /bin/bash -c "mvn clean verify"

# or

docker-compose exec java /bin/bash -c "mvn clean verify -P it"
```

To check the code quality, you can run the following command and then visit the results at [http://localhost:9000/](http://localhost:9000/):

```bash
docker-compose exec java /bin/bash -c "mvn clean verify sonar:sonar -Dsonar.host.url=http://sonar:9000 -P it,report"
```

To server the example run the following command and then visit the website at [http://localhost:8080/examples-housekeeping/index.html](http://localhost:8080/examples-housekeeping/index.html):

```bash
docker-compose exec java /bin/bash -c "cd examples/housekeeping && mvn cargo:run"
```

## Information

### Support

For support, please contact [Christian Gapp](https://github.com/gappc) or
[NOI Techpark](https://noi.bz.it/en).

### Contributing

If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome.

### Versioning

This project uses [SemVer](https://semver.org/) for versioning. For the versions available,
see the [tags on this repository](https://github.com/noi-techpark/odh-alpinebits-server/tags). 

### License

The code in this project is licensed under the Mozilla Public License 2.0 license.
See the LICENSE file for more information.

### Authors

* **Christian Gapp** - *Initial work* - [gappc](https://github.com/gappc)

### Acknowledgements

- [NOI Techpark](https://noi.bz.it/en)
