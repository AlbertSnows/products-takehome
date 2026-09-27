FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY  build.gradle .
COPY gradlew .
COPY settings.gradle .
COPY gradle gradle
COPY src src
RUN   ./gradlew bootJar

FROM bellsoft/liberica-openjre-debian:25-cds
COPY --from=build  /app/build/libs/products.jar  products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "products.jar"]
