FROM java:8-jre
MAINTAINER hughzhao <hugh282003@163.com>

ADD ./target/aop-gateway.jar /app/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/aop-gateway.jar"]

EXPOSE 80