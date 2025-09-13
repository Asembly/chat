FROM gradle:8.14.2-jdk24-alpine as builder
WORKDIR /app
COPY . .
RUN gradle build

FROM openjdk:24
WORKDIR /app
COPY --from=builder /app/build/libs/chat-service.jar ./service.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "service.jar"]