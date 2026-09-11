FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY backend/college-finder/pom.xml .
COPY backend/college-finder/mvnw .
COPY backend/college-finder/.mvn .mvn

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY backend/college-finder/src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]