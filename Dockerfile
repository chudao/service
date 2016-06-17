FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/chudao-0.0.1-SNAPSHOT-standalone.jar /chudao/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/chudao/app.jar"]
