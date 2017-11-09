FROM java:8-jre
MAINTAINER hughzhao <hugh282003@163.com>

ADD ./target/aop-faceset-service.jar /app/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/aop-faceset-service.jar"]

EXPOSE 7000