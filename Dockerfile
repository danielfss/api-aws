FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/api-aws-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1536m", "-jar", "app.jar"]
