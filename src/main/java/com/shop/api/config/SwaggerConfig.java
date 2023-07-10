package com.shop.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String PACKAGE_NAME = "com.shop.api.controller";
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Shop-api Service")
                .description("\"This project is a REST API developed to create, retrieve, update and delete shops.\"")
                .contact(new Contact("Amruta Kulkarni", "https://www.linkedin.com/in/amruta-kulkarni-031bb3160/", "amruta.kulkarni.20121991@gmail.com"))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build();
    }
}
