# Build stage
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copy backend Maven project
COPY backend/college-finder/pom.xml .
COPY backend/college-finder/.mvn .mvn
COPY backend/college-finder/mvnw .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -DskipTests

# Copy source code
COPY backend/college-finder/src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]