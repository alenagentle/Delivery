FROM openjdk:latest
EXPOSE 8080
WORKDIR /app
ARG JAR_FILE=target/delivery-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "/app/delivery-0.0.1-SNAPSHOT.jar"]