# =========================
# Build stage
# =========================
FROM maven:3.9-eclipse-temurin-23 AS build

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

# Copy generated JAR
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# Render supplies PORT
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]