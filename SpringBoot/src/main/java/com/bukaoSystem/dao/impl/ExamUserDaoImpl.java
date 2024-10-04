package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamUserDao;
import com.bukaoSystem.exception.AccountAlreadyRegisteredException;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamUserDaoImpl implements ExamUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long save(ExamUser user) {
        ExamUser existingUser = login(user.getAccount());
        if (existingUser != null) {
            throw new AccountAlreadyRegisteredException("Account already registered: " + user.getAccount());
        }


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
        // 使用 RETURN_GENERATED_KEYS 获取自增ID
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);
//        jdbcTemplate.update(sql.toString(), params.toArray());

        // 检查是否成功生成 ID
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Creating exam failed, no ID obtained.");
        }
    }



    @Override
    public ExamUser findById(Long id) {
        String sql = "SELECT * FROM exam_user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamUser);
    }
    @Override
    public List<ExamUser> findByUsername(String username) {
        String sql = "SELECT * FROM exam_user WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, this::mapRowToExamUser);
    }
    @Override
    public ExamUser findByIdAndUsername(Long id, String username) {
        String sql = "SELECT * FROM exam_user WHERE id = ? AND username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id, username}, this::mapRowToExamUser);
    }
    @Override
    public List<ExamUser>  findAllStudent(Long classId) {
        String sql = "SELECT eu.* FROM exam_user eu " +
                "LEFT JOIN exam_class_student ecs ON eu.id = ecs.studentId AND ecs.classId = ? " +
                "WHERE eu.role = 'student' AND ecs.studentId IS NULL ";
        return jdbcTemplate.query(sql,new Object[]{classId}, this::mapRowToExamUser);
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
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
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

    @Override
    public ExamUser login(String account) {
        try {
            String sql = "SELECT * FROM exam_user WHERE account = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{account}, this::mapRowToExamUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}