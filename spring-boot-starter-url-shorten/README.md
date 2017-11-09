# Spring Boot Starter For Url Shorten

Usage:

Add dependency **spring-boot-starter-url-shorten** to **pom.xml** 

```xml
		<dependency>
			<groupId>com.minivision</groupId>
			<artifactId>spring-boot-starter-url-shorten</artifactId>
			<version>1.0.0</version>
		</dependency>
```

Config **application.properties**

```properties
#还原短链接
urlshorten.shorten-service-root=http://192.168.123.83/
urlshorten.expand-service-url=http://192.168.123.83:6699/expand
#转为短链接
urlshorten.shorten-service-url=http://192.168.123.83:6699/shorten
urlshorten.short-url-prefix=http://aop-gateway:4000/file/
    
```

Inject **UrlShortenService**

```java
  @Autowired
  private UrlShortenService shortService;
```
