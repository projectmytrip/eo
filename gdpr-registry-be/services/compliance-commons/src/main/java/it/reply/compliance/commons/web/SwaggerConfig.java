package it.reply.compliance.commons.web;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
public class SwaggerConfig {

    private final Package basePackage;

    public SwaggerConfig(Package basePackage) {
        this.basePackage = basePackage;
    }

    public Docket api(boolean enable) {
        log.info("Springfox Swagger Configuration - enabled");
        return new Docket(DocumentationType.SWAGGER_2).enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage.getName()))
                .paths(PathSelectors.ant("/internal/**").negate())
                .build();
    }
}
