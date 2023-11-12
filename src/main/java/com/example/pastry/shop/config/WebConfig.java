package com.example.pastry.shop.config;

import com.example.pastry.shop.util.OrdersInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedOrigins("https://sladkarnicata-na-mama.azurewebsites.net/")
                .allowedMethods("PUT", "DELETE", "POST", "GET", "PATCH")
                .allowedHeaders("Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OrdersInterceptor()).addPathPatterns("/api/orders");
    }
}
