package com.homework.sharding.config;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Redick
 * @date 2020/12/9 10:45 上午
 */
@Configuration
@MapperScan(basePackages = "com.homework.sharding.biz.mapper", sqlSessionFactoryRef = "sessionFactory")
public class DataSourceConfiguration {

    /**
     * config datasource 0
     *
     * @return ds0
     */
    @Bean(name = "dataSource0")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.datasource0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    /**
     * config datasource 1
     *
     * @return ds1
     */
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.datasource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    /**
     * config SqlSessionFactory
     * @param dataSource datasource
     * @return SqlSessionFactory
     * @throws Exception Exception
     */
    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.plf.learn.sharding.bean");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    /**
     * config sharingsphere datasource, put ds0 ds1 to datasourceMap and config sharding rules
     * @return sharding datasource
     * @throws SQLException exception
     */
    @Bean
    public DataSource dataSource() throws SQLException {

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // config the true datasource
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("datasource0", dataSource0());
        dataSourceMap.put("datasource1", dataSource1());

        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("tb_order", "datasource${0..1}.tb_order_${0..15}");
        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "datasource${user_id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "tb_order_${user_id % 16}"));


        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        return dataSource;
    }

    /**
     * start Transaction manager
     * @param dataSource dasource
     * @return Transaction manager
     */
    @Bean
    public DataSourceTransactionManager shardTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
