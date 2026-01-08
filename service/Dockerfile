## --- Stage 1: Build (Dùng Maven 3.9 và Java 21) ---
## Dùng tag '3.9-eclipse-temurin-21' để đảm bảo đúng version bạn cần
#FROM maven:3.9-eclipse-temurin-21 AS build
#
## Tạo thư mục làm việc trong container
#WORKDIR /app/service
#
## Copy toàn bộ source code vào container
#COPY . .
#
## Chạy lệnh build (bỏ qua test để build nhanh hơn)
#RUN mvn clean package -DskipTests
#
## --- Stage 2: Run (Chỉ cần Java 21 JRE để chạy, giúp image nhẹ hơn) ---
#FROM eclipse-temurin:21-jre-alpine
#
## Tạo thư mục làm việc
#WORKDIR /app
#
## Copy file .jar đã build từ Stage 1 sang Stage 2
## Lưu ý: Lệnh này tự động lấy file .jar trong folder target
#COPY --from=build /app/service/target/*.jar app.jar
#RUN rm /app/*-plain.jar && mv /app/*.jar /app/app.jar
#
## Mở port 8080 (Phải khớp với server.port trong application.properties)
#EXPOSE 8080
#
## Lệnh chạy ứng dụng
#ENTRYPOINT ["java", "-jar", "app.jar"]

# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-21 AS build

# 1. Đặt thư mục làm việc là /app (quan trọng)
WORKDIR /app

# 2. Copy toàn bộ Repository (bao gồm folder service) vào /app
# Kết quả lúc này ta sẽ có /app/service/pom.xml
COPY . .

# 3. Bây giờ mới di chuyển vào folder service
WORKDIR /app/service

# 4. Chạy lệnh build (lúc này Maven sẽ thấy pom.xml ngay tại chân)
RUN mvn clean package -DskipTests

# --- Stage 2: Run ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar từ đường dẫn đã build
# Lưu ý: file nằm trong /app/service/target/
COPY --from=build /app/service/target/*.jar app.jar

#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

# PORT của Koyeb + JAVA_OPTS tuỳ chỉnh
CMD ["sh", "-c", "java ${JAVA_OPTS:-} -jar app.jar --server.port=${PORT:-8080}"]