FROM openjdk:11-jdk-slim
WORKDIR /app
ARG JAR_FILE=build/libs/gauth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gauth.jar
ENV TZ=Asia/Seoul
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "gauth.jar"]