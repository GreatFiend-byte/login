# Imagen base con Java 17 (compatible con Spring Boot 3.x)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar generado por Maven al contenedor
COPY target/login-0.0.1-SNAPSHOT.jar /app/ms-login.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8083

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "ms-login.jar"]