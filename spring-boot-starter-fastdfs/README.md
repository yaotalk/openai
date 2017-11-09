# Spring Boot FastDFS Starter

Usage:

Add dependency **spring-boot-starter-fastdfs** to **pom.xml** 

```xml
		<dependency>
			<groupId>com.minivision</groupId>
			<artifactId>spring-boot-starter-fastdfs</artifactId>
			<version>1.0.0</version>
		</dependency>
```

Config **application.properties**

```properties
#Optional
fastdfs.connect-timeout=3000
fastdfs.read-timeout=60000
fastdfs.idle-timeout=60000
fastdfs.max-threads=0
fastdfs.max-conn-per-host=100
#Required
fastdfs.tracker-servers=192.168.123.219:22122,192.168.123.220:22122
fastdfs.url-prefix.group1=http://192.168.123.219/
fastdfs.url-prefix.group2=http://192.168.123.220/
    
```

Inject **FdfsService**

```java
  @Autowired
  private FdfsService fdfsService;
```
