FROM eclipse-temurin:21-jre-jammy
LABEL authors="Felipe França"
WORKDIR /app
COPY target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]