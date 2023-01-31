package com.example.pastry.shop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
@EnableGlobalAuthentication(

)
public class MethodSecurityConfig  extends GlobalAuthenticationConfigurerAdapter {
}
