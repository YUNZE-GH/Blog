package com.gh.blog.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 16:43
 */
//表示这个类为一个配置类
@Configuration
@MapperScan(basePackages = "com.gh.blog.dao.monitor", sqlSessionFactoryRef = "monitorSqlSessionFactory")
public class DataSourceConfig_Monitor {
    @Bean(name = "monitorDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.monitor")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "monitorSqlSessionFactory")
    public SqlSessionFactory monitorSqlSessionFactory(@Qualifier("monitorDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/monitor/*.xml"));
        return bean.getObject();
    }
    @Bean("monitorSqlSessionTemplate")
    public SqlSessionTemplate monitorSqlSessionTemplate(
            @Qualifier("monitorSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
