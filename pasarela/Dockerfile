FROM eclipse-temurin:8-jdk-alpine
VOLUME /tmp
COPY target/pasarela-zuul-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-Dhttps.protocols=TLSv1.2", "-jar", "/app.jar"]

