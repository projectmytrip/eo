package com.eni.ioc.utils;

import javax.annotation.PostConstruct;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class CustomConfigurations implements EnvironmentAware {

    private static Environment environment;


    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }
    
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

}

