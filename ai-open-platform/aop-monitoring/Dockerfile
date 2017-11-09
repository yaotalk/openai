FROM java:8-jre
MAINTAINER hughzhao <hugh282003@163.com>

ADD ./target/aop-monitoring.jar /app/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/aop-monitoring.jar"]

EXPOSE 8040 8041