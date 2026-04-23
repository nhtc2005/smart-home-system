FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/smart-home-system-0.0.1.jar smart-home-system-0.0.1.jar
ENTRYPOINT ["java","-jar","smart-home-system-0.0.1.jar"]
