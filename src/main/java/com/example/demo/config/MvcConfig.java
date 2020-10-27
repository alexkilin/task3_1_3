package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers (ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");

    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      //  registry.addResourceHandler("/STATIC/CSS/**").addResourceLocations("/STATIC/CSS/");
        registry.addResourceHandler("/CSS/**").addResourceLocations("/CSS/");
    }

}
