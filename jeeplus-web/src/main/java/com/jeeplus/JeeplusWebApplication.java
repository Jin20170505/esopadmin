package com.jeeplus;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@ServletComponentScan("com.jeeplus")
@ComponentScan(value = "com.jeeplus",lazyInit = true)
@ComponentScan(value = "org.activiti.rest", lazyInit = true)
@ComponentScan({"org.activiti.rest.editor", "org.activiti.rest.diagram"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class
JeeplusWebApplication extends SpringBootServletInitializer {

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle","oracle");
        properties.setProperty("MySQL","mysql");
        properties.setProperty("DB2","db2");
        properties.setProperty("Derby","derby");
        properties.setProperty("H2","h2");
        properties.setProperty("HSQL","hsql");
        properties.setProperty("Informix","informix");
        properties.setProperty("MS-SQL","mssql");
        properties.setProperty("SQL Server", "mssql");
        properties.setProperty("PostgreSQL","postgre");
        properties.setProperty("Sybase","sybase");
        properties.setProperty("Hana","hana");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
    //
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // ??????????????????????????????main???????????????Application?????????
        return builder.sources(JeeplusWebApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(JeeplusWebApplication.class, args);
    }


}