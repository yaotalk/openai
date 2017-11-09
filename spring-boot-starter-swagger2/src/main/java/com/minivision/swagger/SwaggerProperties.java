package com.minivision.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "swagger.ui")
public class SwaggerProperties {
  
    private boolean enable = true;

    private String group = "webapi";
    
    private String basePackage = "[set your api basePackage via 'swagger.ui.basePackage']";

    private String title = "[set an api title via 'swagger.ui.title']";

    private String description = "[add your api description via 'swagger.ui.description']";

    private String version = "1.0.0";

    //private String termsOfServiceUrl = "[set termsOfServiceUrl via 'swagger.ui.termsOfServiceUrl']";

    private String contact = "[set contact via 'swagger.ui.contact']";
    
    private String email = "[set email via 'swagger.ui.email']";

    private String license = "License";

    private String licenseUrl = "https://opensource.org/licenses/MIT";

    private String excludes = "/error*";

}
