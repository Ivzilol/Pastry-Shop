package com.example.pastry.shop.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter((Converter<String, LocalDate>) mappingContext
                -> LocalDate
                .parse(mappingContext.getSource(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        modelMapper.addConverter((Converter<String, LocalDateTime>) mappingContext
                -> LocalDateTime.parse(mappingContext.getSource(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        modelMapper.addConverter((Converter<String, LocalTime>) mappingContext
                -> LocalTime.parse(mappingContext.getSource(),
                DateTimeFormatter.ofPattern("HH:mm:ss")));

        return modelMapper;
    }
}
