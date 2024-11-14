# FROM openjdk:17-jdk-alpine
# WORKDIR /app
# COPY target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","app.jar"]
#
# Build stage
#
# Use Maven image with JDK 17 for the build stage
FROM maven:3.8.2-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:17-jdk-slim
COPY --from=build /target/car-management-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
