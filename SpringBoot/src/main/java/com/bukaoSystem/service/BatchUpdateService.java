package com.bukaoSystem.service;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BatchUpdateService {

    public void batchUpdate(JdbcTemplate jdbcTemplate, String sql, List<Object[]> parameterList) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] parameters = parameterList.get(i);
                for (int j = 0; j < parameters.length; j++) {
                    ps.setObject(j + 1, parameters[j]); // Index starts from 1 in PreparedStatement
                }
            }

            @Override
            public int getBatchSize() {
                return parameterList.size();
            }
        });
    }
}

