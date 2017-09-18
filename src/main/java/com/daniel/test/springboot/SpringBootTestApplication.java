package com.daniel.test.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>项目启动位置</p>
 *
 * @author Daniel_Du
 * @since 2017/9/15 17:00
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.daniel.test.springboot")
@MapperScan(basePackages = "com.daniel.test.springboot.mapper")
public class SpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
        System.out.println("测试项目 已启动!");
    }
}
