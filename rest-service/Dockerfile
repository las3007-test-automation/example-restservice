FROM eclipse-temurin:21-jre-alpine
ADD /target/rest-service.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8081
