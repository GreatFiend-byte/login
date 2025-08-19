# Fase 1: Construir el JAR con Maven
FROM maven:3.8.6-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests  # Genera el JAR en /app/target/

# Fase 2: Imagen final con el JAR
FROM openjdk:17
WORKDIR /app
COPY --from=builder target/login-0.0.1-SNAPSHOT.jar ./app.jar  # Copia el JAR generado
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
