package it.reply.compliance.commons.web;

import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    private final Collection<HandlerInterceptor> interceptor;

    public WebMvcConfig(Collection<HandlerInterceptor> interceptors) {
        this.interceptor = interceptors;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (interceptor != null) {
            interceptor.forEach(registry::addInterceptor);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
    }
}
