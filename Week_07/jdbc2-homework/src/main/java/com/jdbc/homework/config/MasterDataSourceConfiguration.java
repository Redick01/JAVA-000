package com.jdbc.homework.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Redick
 * @date 2020/12/5 6:46 下午
 */
@Configuration
@PropertySource(value = "classpath:jdbc.properties")
//@MapperScan(basePackages = "com.jdbc.homework.biz.mapper.write", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MasterDataSourceConfiguration {

    @Value("${mysql.master.url}")
    private String url;
    @Value("${mysql.master.username}")
    private String username;
    @Value("${mysql.master.password}")
    private String password;

    @Bean(name = "masterDatasource")
    public HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setReadOnly(false);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setMaximumPoolSize(60);
        dataSource.setMinimumIdle(10);
        return dataSource;
    }
}
