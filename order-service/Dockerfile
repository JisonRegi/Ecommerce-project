FROM openjdk:17-jdk-slim
COPY target/*.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]