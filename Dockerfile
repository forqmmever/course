FROM openjdk:8-jdk-alpine

# 指定工作目录
WORKDIR /app

# 复制编译好的 Spring Boot JAR 文件到容器中
COPY target/web-1.0.0.jar.jar /app/app.jar

# 复制数据库脚本到容器中
COPY database.bat /app/database.bat

# 暴露 Spring Boot 应用的端口
EXPOSE 8080

# 运行数据库创建脚本
RUN chmod +x /app/database.bat
RUN /app/database.bat

# 安装 Redis
RUN apk add --no-cache redis

# 设置启动命令
CMD ["java", "-jar", "app.jar"]
