FROM openjdk:24-ea-22-jdk-oracle
WORKDIR /app
COPY /target/heartguess-0.0.1-SNAPSHOT.jar /app/hearthguess.jar
ENTRYPOINT ["java", "-jar", "hearthguess.jar"]