package com.gman.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Anton Mikhaylov on 30.12.2020.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //    @Bean
//    @Description("Spring resource message resolver (from application.yml)")
//    public ResourceBundleMessageSource messageSource() {
//        var msgSource = new ResourceBundleMessageSource();
//        msgSource.setBasename("messages");
//        return msgSource;
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/css/**").addResourceLocations("/static/css/");
    }
}
