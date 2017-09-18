package com.daniel.test.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/18 16:57
 */
@Configuration
@EnableConfigurationProperties(DruidStatProperties.class)
public class MyDruidDataSourceAutoConfigure {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid")// 将为配置的项目 通过这个进行配置.
    public DruidDataSource druidDataSource(Environment env){
        System.out.println("初始化数据源...");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();

        return dataSource;
    }
}
