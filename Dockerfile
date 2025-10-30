# Etapa 1: Compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jdk
WORKDIR /app
# Copia el jar final
COPY --from=build /app/target/*.jar app.jar
# Variable de entorno (puedes sobrescribirla al ejecutar)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
