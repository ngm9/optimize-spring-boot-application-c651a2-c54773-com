FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app
COPY . /app

# Build using Maven installed in the image
RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/video-encoding-microservice-1.0.0.jar"]
