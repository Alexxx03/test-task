package org.job.interview.roombookingservice.configuration;

import org.job.interview.roombookingservice.persistence.model.Weekday;
import org.job.interview.roombookingservice.security.AuthenticatedUserDetailsArgumentResolver;
import org.job.interview.roombookingservice.util.EnumToStringConverter;
import org.job.interview.roombookingservice.util.StringToEnumConverter;
import org.job.interview.roombookingservice.util.StringToEnumConverterFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper(StringToEnumConverter<Weekday> strToEnumConverter, EnumToStringConverter<Weekday> enumToStrConverter) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(strToEnumConverter);
        modelMapper.addConverter(enumToStrConverter);
        modelMapper.getConfiguration().setSkipNullEnabled(true).setAmbiguityIgnored(true);
        modelMapper.createTypeMap(Weekday.class, String.class)
                .setConverter(enumToStrConverter);
        modelMapper.createTypeMap(String.class, Weekday.class)
                .setConverter(strToEnumConverter);
        return modelMapper;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticatedUserDetailsArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }
}

