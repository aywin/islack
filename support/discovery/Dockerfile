FROM openjdk:8
VOLUME /tmp
ADD target/*.jar /app.jar
EXPOSE 8761
ENTRYPOINT [ "java", "-Dspring.profiles.active=docker", "-jar", "/app.jar", "--server.port=8761"]
