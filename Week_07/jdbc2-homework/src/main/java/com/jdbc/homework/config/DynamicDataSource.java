package com.jdbc.homework.config;

import com.jdbc.homework.aop.DynamicDataSourceContainer;
import com.jdbc.homework.enums.DynamicDataSourceEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author Redick
 * @date 2020/12/5 11:39 下午
 */
@Configuration
@PropertySource(value = "classpath:jdbc.properties")
@MapperScan(basePackages = "com.jdbc.homework.biz.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 写数据源
     */
    private final HikariDataSource masterDatasource;
    /**
     * 多个读数据源
     */
    private final List<HikariDataSource> slaveDatasources;

    @Autowired
    public DynamicDataSource(HikariDataSource masterDatasource, HikariDataSource slaveDatasource) {
        this.masterDatasource = masterDatasource;
        this.slaveDatasources = Arrays.asList(slaveDatasource);
    }

    /**
     * 只读数据源个数
     */
    private int readDataSourceSize;

    /**
     * 获取读数据源方式，0：随机，1：轮询
     */
    private int readDataSourcePollPattern = 0;

    private AtomicLong counter = new AtomicLong(0);

    private static final Long MAX_POOL = Long.MAX_VALUE;

    private final Lock lock = new ReentrantLock();



    @Override
    public void afterPropertiesSet() {
        if (this.masterDatasource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(masterDatasource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.WRITE.name(), masterDatasource);
        if (this.slaveDatasources == null) {
            readDataSourceSize = 0;
        } else {
            for(int i = 0; i < slaveDatasources.size(); i++) {
                targetDataSources.put(DynamicDataSourceEnum.READ.name() + i, slaveDatasources.get(i));
            }
            readDataSourceSize = slaveDatasources.size();
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceEnum dynamicDataSourceGlobal = DynamicDataSourceContainer.getDataSource();

        if(dynamicDataSourceGlobal == null
                || dynamicDataSourceGlobal == DynamicDataSourceEnum.WRITE
                || readDataSourceSize <= 0) {
            return DynamicDataSourceEnum.WRITE.name();
        }

        int index = 1;

        if(readDataSourcePollPattern == 1) {
            //轮询方式
            long currValue = counter.incrementAndGet();
            if((currValue + 1) >= MAX_POOL) {
                try {
                    lock.lock();
                    if((currValue + 1) >= MAX_POOL) {
                        counter.set(0);
                    }
                } finally {
                    lock.unlock();
                }
            }
            index = (int) (currValue % readDataSourceSize);
        } else {
            //随机方式
            index = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);
        }
        return dynamicDataSourceGlobal.name() + index;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager sentinelTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
