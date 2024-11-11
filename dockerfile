# Use a base image with JDK 21
FROM openjdk:21-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build file
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

# Copy the Gradle wrapper and necessary files
COPY gradlew ./
COPY gradle ./gradle/
COPY src ./src/



# Copy the built jar file to a new stage
FROM openjdk:21-jdk-slim

# Set the working directory in the final image
WORKDIR /app
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar


# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
