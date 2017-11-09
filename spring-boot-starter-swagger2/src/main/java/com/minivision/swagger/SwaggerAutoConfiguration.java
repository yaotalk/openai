package com.minivision.swagger;

import static springfox.documentation.builders.PathSelectors.any;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger.ui", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SwaggerAutoConfiguration {
  
    @Autowired
    private SwaggerProperties props;

    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(props.getTitle())
                .description(props.getDescription())
                .version(props.getVersion())
                .contact(new Contact(props.getContact(), "", props.getEmail()))
                .license(props.getLicense())
                .licenseUrl(props.getLicenseUrl())
                .build();

        List<ResponseMessage> responseMessages = Arrays.asList(
            new ResponseMessageBuilder() 
            .code(400)
            .message("请求参数错误(Bad Request)")
            .build(),
            new ResponseMessageBuilder() 
            .code(401)
            .message("未授权(Unauthorized)")
            .build(),
            new ResponseMessageBuilder() 
            .code(403)
            .message("禁止访问(Forbidden)")
            .build(),
            new ResponseMessageBuilder() 
            .code(405)
            .message("参数格式错误(invalid input)")
            .build(),
            new ResponseMessageBuilder()   
            .code(500)
            .message("服务内部错误")
            .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(props.getGroup())
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage(props.getBasePackage()))
                .paths(any())
                .build().pathMapping("/")
                .directModelSubstitute(Date.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .forCodeGeneration(true);
    }

}
