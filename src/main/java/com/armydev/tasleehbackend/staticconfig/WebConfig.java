package com.armydev.tasleehbackend.staticconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  // @Override
  // public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
  // registry.addResourceHandler("/uploads/**")
  // .addResourceLocations("file:./uploads/")
  // .setCachePeriod(3600)
  // .resourceChain(true);
  // }
}