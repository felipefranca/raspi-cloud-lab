FROM eclipse-temurin:21
LABEL authors="Felipe França"
WORKDIR /app
COPY target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]