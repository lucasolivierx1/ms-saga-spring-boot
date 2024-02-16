FROM amazoncorretto:21-alpine-jdk
COPY build/libs/*SNAPSHOT.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]