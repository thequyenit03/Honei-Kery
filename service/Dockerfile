# Giai đoạn 1: Build ứng dụng (Sử dụng Maven)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy file pom.xml và tải các dependency (để tận dụng cache layer của Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ mã nguồn và build
COPY src ./src
RUN mvn clean package -DskipTests

# Giai đoạn 2: Chạy ứng dụng (Sử dụng JRE nhẹ)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar đã build từ giai đoạn 1
# Lưu ý: Tên file jar có thể thay đổi tùy project, dòng này lấy file jar đầu tiên tìm thấy trong target
COPY --from=build /app/target/*.jar app.jar

# Expose port mặc định của Spring Boot
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]