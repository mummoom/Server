package com.example.mummoomserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket restAPI() {
            ParameterBuilder aParameterBuilder = new ParameterBuilder();
            aParameterBuilder.name("X-AUTH-TOKEN") //헤더 이름
                    .description("JWT Token 입력칸") //설명
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .build();

            List<Parameter> aParameters = new ArrayList<>();
            aParameters.add(aParameterBuilder.build());
            return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.mummoomserver"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("멈뭄 API")
                .version("1.0.0")
                .description("멈뭄 api 리스트 입니다.")
                .build();
    }
}