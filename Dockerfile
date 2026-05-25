# ─────────────────────────────────────────────────────────────
# Dockerfile — LoginSignupCRUD
# ─────────────────────────────────────────────────────────────
# Stage 1: Build the WAR using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (faster rebuilds)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy all source files and build the WAR
COPY src ./src
RUN mvn package -DskipTests -q

# ─────────────────────────────────────────────────────────────
# Stage 2: Run on Tomcat 10 (Jakarta EE 10 compatible)
FROM tomcat:10.1-jdk17-temurin

# Remove default Tomcat apps (clean slate)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR from Stage 1 and deploy as ROOT
# ROOT = app runs at / instead of /LoginSignupCRUD/
COPY --from=build /app/target/LoginSignupCRUD.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
