package com.daniel.test.springboot.config;

import com.daniel.test.springboot.filter.CipherFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 自定义过滤器
 */
@Configuration
public class FilterRegister {

    @Value("${filter.cipher}")
    private String path;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CipherFilter());
        registrationBean.addUrlPatterns(path);
        return registrationBean;
    }
}
