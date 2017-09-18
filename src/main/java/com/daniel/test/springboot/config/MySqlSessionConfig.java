package com.daniel.test.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/18 17:06
 */
@Configuration
@Import(MyDruidDataSourceAutoConfigure.class)
public class MySqlSessionConfig {

    @Value("${mybatis.mapper-locations}")
    private String MAPPER_LOCATIONS;
    @Value("${mybatis.type-aliases-package}")
    private String TYPE_ALIASES_PACKAGE;

    @Autowired
    private DruidDataSource druidDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource){
        System.out.println("sqlSessionFactory init");
        // 创建 SQLSessionFactory
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);

        // 添加分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        // 添加插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        // 添加 xml 目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
            return sqlSessionFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource){
        System.out.println("sqlSessionTemplate init");
        return new SqlSessionTemplate(sqlSessionFactory(dataSource));
    }
}
