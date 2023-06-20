FROM openjdk:11
COPY /var/jenkins_home/workspace/store-manager/build/libs/*.jar store-manager.jar
ENTRYPOINT ["java","-jar", "/store-manager.jar"]