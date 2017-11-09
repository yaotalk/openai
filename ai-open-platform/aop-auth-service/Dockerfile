FROM java:8-jre
MAINTAINER hughzhao <hugh282003@163.com>

ADD ./target/aop-auth-service.jar /app/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/aop-auth-service.jar"]

EXPOSE 5000