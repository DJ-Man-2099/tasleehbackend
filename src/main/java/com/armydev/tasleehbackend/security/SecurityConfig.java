package com.armydev.tasleehbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Allow requests from any origin
                .allowedMethods("GET", "POST", "PATCH", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("/assets/images/**")
        // .addResourceLocations("classpath:/static/assets/assets/images/");
        // registry.addResourceHandler("/scripts/**")
        // .addResourceLocations("classpath:/static/scripts/");
    }
}
