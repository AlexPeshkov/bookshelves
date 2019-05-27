package com.home.books.configuration;

import com.home.books.domain.FictionBook;
import com.home.books.domain.NonFictionBook;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.home.books.controller"))
                .paths(PathSelectors.any())
                .build()
                    .apiInfo(apiInfo())
                .additionalModels(typeResolver.resolve(FictionBook.class))
                .additionalModels(typeResolver.resolve(NonFictionBook.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "BOOK SHELVES REST API",
                "Books description of API",
                "API TOS",
                "Terms of service",
                new Contact("Alexander Peshkov", "xxx", "alexander.peshkov27@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
