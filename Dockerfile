FROM openjdk:11
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} store-manager.jar
ENTRYPOINT ["java","-jar", "/store-manager.jar"]