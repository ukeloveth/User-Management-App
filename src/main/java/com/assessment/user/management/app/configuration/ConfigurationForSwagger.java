package com.assessment.user.management.app.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigurationForSwagger implements WebMvcConfigurer {



//    @Bean
//    public Docket swaggerConfig() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiDetails())
//                .securityContexts(List.of(securityContext()))
//                .securitySchemes(List.of(apiKey()))
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.assessment.usermanagementapi"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    private SecurityReference bearerAuthReference() {
//        return new SecurityReference("Bearer", new AuthorizationScope[0]);
//    }
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }



//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return List.of(new SecurityReference("Bearer", authorizationScopes));
//    }



//    private ApiKey apiKey() {
//        return new ApiKey("Bearer", "Authorization", "header");
//    }
//
//    private ApiInfo apiDetails() {
//        return new ApiInfo(
//                "User Management APP",
//                "API for User Management APP",
//                "1.0",
//                "Free to use",
//                new springfox.documentation.service.Contact("", "https://github.com/ukeloveth", "ukeloveth247@gmail.com"),
//                "API License",
//                "",
//                Collections.emptyList()
//        );
//    }


}