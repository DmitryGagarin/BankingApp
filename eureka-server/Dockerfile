FROM openjdk:19-jdk-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
RUN echo 'eureka server image generated'
MAINTAINER Dmitrii
