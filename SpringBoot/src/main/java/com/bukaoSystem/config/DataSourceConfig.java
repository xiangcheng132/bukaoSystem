package com.bukaoSystem.config;

import com.bukaoSystem.db.DataBaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataBaseConnection dataBaseConnection() {
        return new DataBaseConnection();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataBaseConnection dataBaseConnection) {
        return new JdbcTemplate(dataBaseConnection);
    }
}
