# --- Stage 1: Build ứng dụng ---
# Sử dụng Maven và JDK 21 để build file .jar
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy file cấu hình maven trước để tận dụng cache
COPY pom.xml .
# Download dependencies (giúp build nhanh hơn ở các lần sau)
RUN mvn dependency:go-offline

# Copy toàn bộ source code và build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Chạy ứng dụng ---
# Sử dụng JRE 21 (bản nhẹ) để chạy ứng dụng
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar từ Stage 1 sang Stage 2
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]