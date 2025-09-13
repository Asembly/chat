FROM gradle:8.14.2-jdk24-alpine as builder
WORKDIR /app
COPY . .
RUN gradle build

FROM openjdk:24
WORKDIR /app
COPY --from=builder /app/build/libs/eureka-server.jar ./app.jar
EXPOSE 9099
ENTRYPOINT ["java", "-jar", "app.jar"]