FROM openjdk:17-alpine as base
 
FROM base as dev
WORKDIR /app
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

FROM base as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src src
RUN ./mvnw clean package

FROM base as prod
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD [ "java", "-jar", "app.jar" ]
