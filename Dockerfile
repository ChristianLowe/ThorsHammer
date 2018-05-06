
FROM openjdk:8-jdk-alpine

ADD config.txt .

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
