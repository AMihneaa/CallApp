# ---------- STAGE 1: Build ----------

# Folosește imaginea oficială de Maven și OpenJDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Setează directorul de lucru în container
WORKDIR /build

# Copiază fișierul pom.xml și directorul src în container
COPY pom.xml .
COPY src ./src

# Rulează comanda Maven pentru a construi aplicația, ignorând testele
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Runtime ----------

# Folosește imaginea de OpenJDK 17 pentru a rula aplicația
FROM openjdk:17-jdk-slim

# Setează directorul de lucru în container
WORKDIR /app

# Copiază fișierul JAR generat în timpul build-ului în container
COPY --from=build /build/target/*.jar app.jar

# Expune portul 8080 pentru a putea accesa aplicația
EXPOSE 8080

# Comanda de rulare a aplicației Java
ENTRYPOINT ["java", "-jar", "app.jar", "--server.address=0.0.0.0"]
