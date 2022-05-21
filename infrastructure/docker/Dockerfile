FROM maven:3-jdk-8 as base

RUN mkdir -p /code
WORKDIR /code

# Dev
FROM base as dev

RUN mkdir -p /code
WORKDIR /code
#CMD [ "sh", "-c", "mvn clean install && cd application-spring && mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.fork=false"]
#RUN pwd
#RUN ls -la
##RUN mvn clean install
#
WORKDIR /code/application-spring
CMD [ "mvn", "spring-boot:run", "-Dspring-boot.run.profiles=local", "-Dspring-boot.run.fork=false" ]

# Package
FROM base as package

COPY . /code
WORKDIR  /code/application-spring
RUN mvn -DskipTests package

# Build
FROM openjdk:11-jre-buster as build

RUN mkdir -p /home/app
WORKDIR /home/app
COPY --from=package /code/application-spring/target/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "it.bz.opendatahub.alpinebitsserver.application.spring.Application"]
