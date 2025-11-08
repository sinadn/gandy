package com.example.gandy.cors;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    @Value("*")
    private String allowedMethods;

    @Value("*")
    private String allowedHeaders;

    @Value("/**")
    private String corsConfiguration;



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping(corsConfiguration).allowedOrigins("http://localhost:3000" , "https://www.gandimobile.ir","https://gandimobile.ir","http://87.107.104.119","http://87.107.104.119:1221","http://87.107.104.119:1221/login","https://87.107.104.119" , "https://gandimobile.ir:8443" , "https://www.gandimobile.ir:8443" , "https://gandimobile.ir:8443/api/gateway-callback" , "https://www.gandimobile.ir:8443/api/gateway-callback", "https://gandi-app.vercel.app" , "https://www.ordercode.ir" ,"https://ordercode.ir").allowedMethods(allowedMethods).allowedHeaders(allowedHeaders).allowCredentials(true).maxAge(600);
            }
        };
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }

}
