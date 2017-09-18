package com.daniel.test.springboot.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/18 17:10
 */
@Configuration
@Import(MyDruidDataSourceAutoConfigure.class)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class MyTransactionManager {

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource){
        System.out.println("transactionManager inti");
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager ptm){
        System.out.println("transactionInterceptor init");
        Properties attributes = new Properties();
        // 新增
        attributes.setProperty("insert*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("add*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("new*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("create*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("register*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        // 修改
        attributes.setProperty("update*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        // 删除
        attributes.setProperty("delete*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        // 自定义
        attributes.setProperty("app*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("getReadCommited*", "PROPAGATION_REQUIRES_NEW,ISOLATION_READ_COMMITTED");
        attributes.setProperty("batch*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");

        //查询
        attributes.setProperty("get*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("find*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("select*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        attributes.setProperty("query*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(ptm, attributes);
        return transactionInterceptor;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor(TransactionInterceptor transactionInterceptor){
        System.out.println("aspectJExpressionPointcutAdvisor init");
        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setExpression("execution(* com.daniel.test.springboot.service..*.*(..))");
        aspectJExpressionPointcutAdvisor.setAdvice(transactionInterceptor);
        return aspectJExpressionPointcutAdvisor;
    }
}
