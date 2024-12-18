FROM openjdk:21

LABEL authors="aleshina-e"

WORKDIR /app

COPY build/libs/bookportal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]