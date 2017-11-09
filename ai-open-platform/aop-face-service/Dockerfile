FROM java:8-jre
MAINTAINER hughzhao <hugh282003@163.com>

ADD ./target/aop-face-service.jar /app/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/aop-face-service.jar"]

EXPOSE 6000