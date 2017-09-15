package com.daniel.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>项目启动位置</p>
 *
 * @author Daniel_Du
 * @since 2017/9/15 17:00
 */
@SpringBootApplication
public class SpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
        System.out.println("测试项目 已启动!");
    }
}
