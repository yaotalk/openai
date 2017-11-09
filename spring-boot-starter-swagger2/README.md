# spring-boot-starter-swagger2

spring-boot-starter-swagger2 via springbox

## 依赖

```xml
<dependency>
    <groupId>com.minivision</groupId>
    <artifactId>spring-boot-starter-swagger2</artifactId>
    <version>${starter-swagger.version}</version>
</dependency>
```

## 配置

```properties
# application.properties
#Required
swagger.ui.basePackage=xyz
swagger.ui.title=xyz
swagger.ui.description=xyz
swagger.ui.contact=xyz
swagger.ui.email=xyz

#Optional
swagger.ui.group=xyz
swagger.ui.version=xyz
...
```

更多配置参数见: [SwaggerProperties.java]

线上环境关闭swagger ui, 可以配置参数 `swagger.ui.enable=false`

## 使用
[http://host:port/swagger-ui.html](http://localhost:8080/swagger-ui.html)



