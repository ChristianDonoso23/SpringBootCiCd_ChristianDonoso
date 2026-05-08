# Etapa 1: Build (Compilación)
# Usamos Java 21 porque es la versión definida en tu toolchain
FROM eclipse-temurin:21-jdk-jammy AS build
COPY . .
RUN chmod +x ./gradlew
# Compilamos el proyecto. El nombre del jar será spring-lab-0.0.1-SNAPSHOT.jar
RUN ./gradlew clean build -x test

# Etapa 2: Run (Ejecución)
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /build/libs/spring-lab-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto 8081 que definiste en tu application.yml
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]