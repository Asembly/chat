FROM gradle:8.14.2-jdk24-alpine as builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew && ./gradlew build -x test

FROM openjdk:24 as tester
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew && ./gradlew test -Dspring.profiles.active=test

FROM openjdk:24
WORKDIR /app
COPY --from=builder /app/build/libs/app.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]