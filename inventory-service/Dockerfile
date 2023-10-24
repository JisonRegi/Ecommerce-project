FROM openjdk:17-jdk-slim
COPY target/*.jar inventory-service.jar
ENTRYPOINT ["java","-jar","/inventory-service.jar"]