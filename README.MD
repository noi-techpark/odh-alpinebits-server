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
- [How to request data?](#How-to-request-data)

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
java -jar application-spring/target/application-spring-2.0.1.jar it.bz.opendatahub.alpinebitsserver.application.spring.Application 
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

## How to request data?

You can use the [AlpineBits Protocol Test Client](https://alpinebits.testingmachine.eu/#/client) to request data from the [Open Data Hub](https://opendatahub.bz.it/) using the AlpineBits Open Standard.

In the test client you have to specify the request settings:

```
end point (URL): https://alpinebits.opendatahub.bz.it/AlpineBits
user name: someuser
password: somesecret
X-AlpineBits-ClientProtocolVersion: 2018-10
X-AlpineBits-ClientID (optional): development.alpinebits.org-test-client
action: OTA_Ping:Handshaking (since 2018-10)
request (optional): handshaking.xml
```

The *action* setting requires you to upload an XML file. You can create a plain text file called *handshaking.xml* on your machine and fill in the following XML content: 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<OTA_PingRQ xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns="http://www.opentravel.org/OTA/2003/05" xsi:schemaLocation="http://www.opentravel.org/OTA/2003/05 OTA_PingRQ.xsd" Version="8.000">

    <EchoData>
    {
      "versions": [{
        "version": "2017-10"
      },
      {
        "version": "2018-10",
        "actions": [
          {
            "action": "action_OTA_Ping"
          },{
            "action": "action_OTA_HotelDescriptiveInfo_Inventory",
            "supports": ["OTA_HotelRatePlan_BaseRates_deltas"]
          },{
            "action": "action_OTA_HotelDescriptiveContentNotif_Inventory",
            "supports": ["OTA_HotelDescriptiveContentNotif_Inventory_use_rooms"]
          },{
            "action": "action_OTA_HotelRatePlan_BaseRates",
            "supports": ["OTA_HotelRatePlan_BaseRates_deltas"]
          },{
            "action": "action_OTA_HotelRatePlanNotif_RatePlans",
            "supports": [
              "OTA_HotelRatePlanNotif_accept_overlay",
              "OTA_HotelRatePlanNotif_accept_Supplements",
              "OTA_HotelRatePlanNotif_accept_RatePlan_BookingRule",
              "OTA_HotelRatePlanNotif_accept_FreeNightsOffers",
              "OTA_HotelRatePlanNotif_accept_FamilyOffers"
            ]
          }
        ]
      }]
    }
    </EchoData>

</OTA_PingRQ>
```

The response should look like this:

```xml
end point response (status = 200)
=================================

headers
-------

Date: Mon, 09 Mar 2020 07:33:39 GMT
Server: Apache/2.4.39 (Amazon) OpenSSL/1.0.2k-fips
Content-Length: 1640
Connection: close


body
----

<?xml version="1.0" encoding="UTF-8" standalone="yes"?><OTA_PingRS xmlns="http://www.opentravel.org/OTA/2003/05" Version="8.000"><Success></Success><Warnings><Warning Type="11" Status="ALPINEBITS_HANDSHAKE">{"versions":[{"version":"2018-10","actions":[{"action":"action_OTA_HotelDescriptiveContentNotif_Inventory","supports":["OTA_HotelDescriptiveContentNotif_Inventory_use_rooms"]},{"action":"action_OTA_Ping"},{"action":"action_OTA_HotelDescriptiveInfo_Inventory"}]},{"version":"2017-10"}]}</Warning></Warnings><EchoData>
    {
      "versions": [{
        "version": "2017-10"
      },
      {
        "version": "2018-10",
        "actions": [
          {
            "action": "action_OTA_Ping"
          },{
            "action": "action_OTA_HotelDescriptiveInfo_Inventory",
            "supports": ["OTA_HotelRatePlan_BaseRates_deltas"]
          },{
            "action": "action_OTA_HotelDescriptiveContentNotif_Inventory",
            "supports": ["OTA_HotelDescriptiveContentNotif_Inventory_use_rooms"]
          },{
            "action": "action_OTA_HotelRatePlan_BaseRates",
            "supports": ["OTA_HotelRatePlan_BaseRates_deltas"]
          },{
            "action": "action_OTA_HotelRatePlanNotif_RatePlans",
            "supports": [
              "OTA_HotelRatePlanNotif_accept_overlay",
              "OTA_HotelRatePlanNotif_accept_Supplements",
              "OTA_HotelRatePlanNotif_accept_RatePlan_BookingRule",
              "OTA_HotelRatePlanNotif_accept_FreeNightsOffers",
              "OTA_HotelRatePlanNotif_accept_FamilyOffers"
            ]
          }
        ]
      }]
    }
    </EchoData></OTA_PingRS>
```

