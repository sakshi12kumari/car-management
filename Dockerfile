FROM openjdk:11-jre-slim

COPY target/car-management.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
