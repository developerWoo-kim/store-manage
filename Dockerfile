FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} store-manager.jar
ENTRYPOINT ["java","-jar", "/store-manager.jar"]