# ==========================================
# Stage 1: Build Spring Boot application
# ==========================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy backend Maven configuration
COPY backend/pom.xml .

# Download dependencies first for better Docker caching
RUN mvn dependency:go-offline -B

# Copy backend source code
COPY backend/src ./src

# Build Spring Boot JAR
RUN mvn clean package -DskipTests


# ==========================================
# Stage 2: Run Spring Boot application
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy generated JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Render provides the PORT environment variable.
# 8081 is used locally if PORT is not provided.
EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar app.jar"]