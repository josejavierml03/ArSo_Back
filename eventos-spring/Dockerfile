# Dockerfile - Java 8
FROM eclipse-temurin:8-jdk-alpine
VOLUME /tmp
COPY target/eventos-spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
