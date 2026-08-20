# Stage 1: Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper files and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Convert line endings for mvnw in case of Windows CRLF and ensure execution permissions
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Download dependencies to cache them in Docker layer
RUN ./mvnw dependency:go-offline -B

# Copy application source code
COPY src ./src

# Build the executable JAR without running tests
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user and group for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy built JAR artifact from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose server port
EXPOSE 8080

# Environment variables
ENV PORT=8080

# Run the application
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-Djava.net.preferIPv4Addresses=true", "-jar", "app.jar"]
