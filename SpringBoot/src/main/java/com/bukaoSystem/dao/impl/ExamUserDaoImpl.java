package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamUserDao;
import com.bukaoSystem.model.ExamUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamUserDaoImpl implements ExamUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ExamUser user) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_user (username, account, password");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");

        List<Object> params = new ArrayList<>();
        params.add(user.getUsername());
        params.add(user.getAccount());
        params.add(user.getPassword());

        if (user.getRole() != null) {
            sql.append(", role");
            values.append(", ?");
            params.add(user.getRole());
        }

        if (user.getEmail() != null) {
            sql.append(", email");
            values.append(", ?");
            params.add(user.getEmail());
        }

        if (user.getPhone() != null) {
            sql.append(", phone");
            values.append(", ?");
            params.add(user.getPhone());
        }

        if (user.getSex() != null) {
            sql.append(", sex");
            values.append(", ?");
            params.add(user.getSex());
        }

        if (user.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(user.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }



    @Override
    public ExamUser findById(Long id) {
        String sql = "SELECT * FROM exam_user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamUser);
    }

    @Override
    public List<ExamUser> findAll() {
        String sql = "SELECT * FROM exam_user";
        return jdbcTemplate.query(sql, this::mapRowToExamUser);
    }

    @Override
    public void update(ExamUser user) {
        StringBuilder sql = new StringBuilder("UPDATE exam_user SET username = ?, account = ?, password = ?");
        List<Object> params = new ArrayList<>();

        params.add(user.getUsername());
        params.add(user.getAccount());
        params.add(user.getPassword());

        if (user.getRole() != null) {
            sql.append(", role = ?");
            params.add(user.getRole());
        }

        if (user.getEmail() != null) {
            sql.append(", email = ?");
            params.add(user.getEmail());
        }

        if (user.getPhone() != null) {
            sql.append(", phone = ?");
            params.add(user.getPhone());
        }

        if (user.getSex() != null) {
            sql.append(", sex = ?");
            params.add(user.getSex());
        }

        if (user.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(user.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(user.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    private ExamUser mapRowToExamUser(ResultSet rs, int rowNum) throws SQLException {
        ExamUser user = new ExamUser();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setAccount(rs.getString("account"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setSex(rs.getString("sex"));
        user.setCreateTime(rs.getString("createTime"));
        return user;
    }
}