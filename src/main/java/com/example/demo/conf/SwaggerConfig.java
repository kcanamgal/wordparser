package com.example.demo.conf;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private final String SWAGGER_TITLE = "Swagger测试标题";
    private final String SWAGGER_DESCRIPTION = "描述";
    private final String SWAGGER_API_CONTACT = "contact";
    private final String SWAGGER_SERVICE_URL = "https://www.baidu.com";
    private final String SWAGGER_API_CONTACT_URL = "https://www.cnblogs.com";
    private final String SWAGGER_API_CONTACT_EMAIL = "lmpt10001011@gmail.com";
    private final String SWAGGER_API_VERSION = "1.0";

    @Bean
    public Docket createRestApi() {
        // 返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(SWAGGER_TITLE)
                .description(SWAGGER_DESCRIPTION)
                .termsOfServiceUrl(SWAGGER_SERVICE_URL)
                .contact(new Contact(SWAGGER_API_CONTACT, SWAGGER_API_CONTACT_URL, SWAGGER_API_CONTACT_EMAIL))
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 让所有项目基础路径 + /swagger-ui/** 的url访问
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui")
                .resourceChain(false);
    }
}
