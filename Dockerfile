FROM openjdk:17-jdk
WORKDIR /bookstore-management
ARG VERSION
ARG SERVICE_NAME=BookstoreManagement
COPY build/libs/${SERVICE_NAME}-${VERSION}.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
