# Fase 1: Construir el JAR con Maven (imagen actualizada y verificada)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Imagen final con JRE (más ligera)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/login-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
