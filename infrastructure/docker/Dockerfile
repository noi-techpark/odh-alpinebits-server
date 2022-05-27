FROM maven:3-jdk-8-alpine as base

RUN mkdir -p /code
WORKDIR /code

# Dev
FROM base as dev

# Build
FROM base as build

COPY ./ /code
RUN ls -ls && mvn -DskipTests install

CMD ["mvn", "spring-boot:run", "-pl", "application-spring"]