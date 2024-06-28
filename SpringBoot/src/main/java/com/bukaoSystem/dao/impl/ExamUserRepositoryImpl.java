package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamUserRepository;
import com.bukaoSystem.modal.ExamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExamUserRepositoryImpl implements ExamUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ExamUser user) {
        String sql = "INSERT INTO exam_user (username, account, password, role, email, phone, sex, createTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getAccount(), user.getPassword(), user.getRole(), user.getEmail(), user.getPhone(), user.getSex(), user.getCreateTime());
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
        String sql = "UPDATE exam_user SET username = ?, account = ?, password = ?, role = ?, email = ?, phone = ?, sex = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getAccount(), user.getPassword(), user.getRole(), user.getEmail(), user.getPhone(), user.getSex(), user.getCreateTime(), user.getId());
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
        user.setCreateTime(rs.getTimestamp("createTime").toLocalDateTime());
        return user;
    }
}