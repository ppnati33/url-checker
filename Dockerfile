FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} url-checker-0.0.1-SNAPSHOT.jar.jar
ENTRYPOINT ["java","-jar","/url-checker-0.0.1-SNAPSHOT.jar.jar"]