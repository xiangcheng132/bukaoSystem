package com.bukaoSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input == null) {
            System.out.println("Sorry, unable to find application.properties");
            return null;
        }
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("spring.datasource.url"); // URL指向要访问的数据库名mysql
        String r_username = properties.getProperty("spring.datasource.username"); // mysql配置时的用户名
        String r_pwd = properties.getProperty("spring.datasource.password"); // mysql配置时的密码
        String driver = properties.getProperty("spring.datasource.driver-class-name"); // 驱动类

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(r_username);
        dataSource.setPassword(r_pwd);
        return dataSource;
    }
}
