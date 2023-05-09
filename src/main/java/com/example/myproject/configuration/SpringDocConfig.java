package com.example.myproject.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());

    }
    public Info infoAPI() {
        return new Info().title("SpringDoc-Demo")
                .description("TP étude de cas")
                .contact(contactAPI());

    }
    public Contact contactAPI() {
        return new Contact().name("Equipe ASI II")
                .email("nourchen.hedfi@esprit.tn");
        //.url("https://www.linkedin.com/in/mohamed-mokhtar-jaafar-82aa891b2/");
    }
//    @Bean
//    public GroupedOpenApi productPublicApi() {
//        return GroupedOpenApi.builder()
//
//                .group("Only Product Management API")
//                .pathsToMatch("/product/**")
//                .pathsToExclude("**")
//                .build();
//
//    }

}

