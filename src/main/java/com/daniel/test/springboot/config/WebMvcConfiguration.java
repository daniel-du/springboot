package com.daniel.test.springboot.config;

import com.daniel.test.springboot.interceptor.AppLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/25 17:35
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        // 定制 URL 匹配规则
        configurer.setUseSuffixPatternMatch(false);
        configurer.setUseTrailingSlashMatch(true);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AppLoginInterceptor appLoginInterceptor = new AppLoginInterceptor();
        registry.addInterceptor(appLoginInterceptor)
                .addPathPatterns("/**")     // 拦截所有
                .excludePathPatterns("/callback/**")    //排除 第三方回调url
                .excludePathPatterns("/h5/**")   //排除 h5,活动 url
                .excludePathPatterns("/**/not/*");  //排除 app免登录url

//        registry.addInterceptor(new AppLoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/excludeUrl/**");// 可以配置多个拦截器, 为每个拦截器配置不同的拦截规则

        super.addInterceptors(registry);
    }

    /**
     * 全局跨域访问设置
     * allowedOrigins指定可以访问符合mapping规则的接口的域名和端口
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // registry.addMapping("/app/**").allowedOrigins("http://192.168.1.68:8081");
        registry.addMapping("/**").
                allowedOrigins("http://192.168.1.47:8080", "http://192.168.1.188", "http://192.168.1.100:8080", "http://192.168.1.68:8081");
        super.addCorsMappings(registry);
    }
}
