FROM openjdk:19-jdk-oracle
WORKDIR /app

# Specify the JAR file to be copied
ARG JAR_FILE=target/*.jar

# Copy the JAR file from your host machine into the working directory
COPY ${JAR_FILE} app.jar

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Author/Maintainer of the Docker image
LABEL maintainer="Dmitrii"