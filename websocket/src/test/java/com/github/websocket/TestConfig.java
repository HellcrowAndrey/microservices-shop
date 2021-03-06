package com.github.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
public class TestConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();

        properties.setProperty("swagger.enabled", "false");

        pspc.setProperties(properties);
        return pspc;
    }

}
