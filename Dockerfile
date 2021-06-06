FROM adoptopenjdk/openjdk11

ADD target/company-test-0.0.1-SNAPSHOT.jar company-test.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "company-test.jar"]