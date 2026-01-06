# --- Stage 1: Build (Dùng Maven 3.9 và Java 21) ---
# Dùng tag '3.9-eclipse-temurin-21' để đảm bảo đúng version bạn cần
FROM maven:3.9-eclipse-temurin-21 AS build

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ source code vào container
COPY . .

# Chạy lệnh build (bỏ qua test để build nhanh hơn)
RUN mvn clean package -DskipTests

# --- Stage 2: Run (Chỉ cần Java 21 JRE để chạy, giúp image nhẹ hơn) ---
FROM eclipse-temurin:21-jre-alpine

# Tạo thư mục làm việc
WORKDIR /app

# Copy file .jar đã build từ Stage 1 sang Stage 2
# Lưu ý: Lệnh này tự động lấy file .jar trong folder target
COPY --from=build /app/target/*.jar /app/
RUN rm /app/*-plain.jar && mv /app/*.jar /app/app.jar
# Mở port 8080 (Phải khớp với server.port trong application.properties)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]