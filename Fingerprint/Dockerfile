FROM openjdk:8-jdk-alpine
MAINTAINER ABANCESA aba.27.0309@gmail.com
VOLUME /tmp
EXPOSE 9003
ARG JAR_FILE=target/Fingerprint-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]