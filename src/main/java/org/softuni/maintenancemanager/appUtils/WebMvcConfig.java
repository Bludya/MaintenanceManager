package org.softuni.maintenancemanager.appUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final StringEscapeFilter stringEscapeFilter;

    @Autowired
    public WebMvcConfig(StringEscapeFilter stringEscapeFilter) {
        this.stringEscapeFilter = stringEscapeFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(stringEscapeFilter);
    }
}
