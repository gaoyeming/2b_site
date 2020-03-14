package com.yeming.site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yeming.gao
 * @Description: swagger2自动生成api插件
 * @date 2019/11/07 16:39
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yeming.site.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("yeming.gao", "https://github.com/gaoyeming/site.git", "yeming.gao@aliyun.com");
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2")
                .description("个人网站")
                .termsOfServiceUrl("https://github.com/gaoyeming")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

}
