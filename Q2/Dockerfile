FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Q2-0.0.1-SNAPSHOT.jar /app/q4.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/q4.jar"]

