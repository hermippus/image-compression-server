FROM gradle:8.11-jdk21 AS builder

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/image-compression-server-2.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]