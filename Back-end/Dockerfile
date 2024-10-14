FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .


RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim AS build
COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:resolve

COPY src src

RUN ./mvnw package
FROM openjdk:17-jdk-slim
WORKDIR demo
COPY --from=build target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]