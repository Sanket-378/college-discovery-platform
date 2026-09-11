# =========================
# Build stage
# =========================
FROM maven:3.9.11-eclipse-temurin-23 AS build

WORKDIR /app

# Copy Maven project
COPY backend/pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -DskipTests

# Copy source code
COPY backend/src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests


# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copy generated Spring Boot JAR
COPY --from=build /app/target/*.jar app.jar

# Render provides PORT automatically.
# Spring Boot will use 8081 if PORT is not provided.
ENV PORT=8081

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]