package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class ExamClassDaoImpl implements ExamClassDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long save(ExamClass examClass) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_class (name");
        StringBuilder values = new StringBuilder(" VALUES (?");
        List<Object> params = new ArrayList<>();
        params.add(examClass.getName());

        if (examClass.getComment() != null) {
            sql.append(", comment");
            values.append(", ?");
            params.add(examClass.getComment());
        }

        if (examClass.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examClass.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);

//        jdbcTemplate.update(sql.toString(), params.toArray());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    @Override
    public ExamClass findById(Long id) {
        String sql = "SELECT * FROM exam_class WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamClass);
    }

    @Override
    public List<ExamClass> findAll() {
        String sql = "SELECT * FROM exam_class";
        return jdbcTemplate.query(sql, this::mapRowToExamClass);
    }
    @Override
    public List<ExamClassDto> findAllwithteacher() {
        String sql = "SELECT exam_class.id, exam_class.name, exam_class.comment, eu.username AS teachername, exam_class.createTime  FROM exam_class " +
                "LEFT JOIN exam_class_teacher ect ON exam_class.id = ect.classId " +
                "LEFT JOIN exam_user eu ON ect.teacherId = eu.id ";
        return jdbcTemplate.query(sql, this::mapRowToExamClassdto);
    }
    @Override
    public List<ExamClassDto> findAllwithTeacherById(Long classId) {
        String sql = "SELECT exam_class.id, " +
                "exam_class.name, " +
                "exam_class.comment, " +
                "eu.username AS teachername, " +
                "exam_class.createTime " +
                "FROM exam_class " +
                "LEFT JOIN exam_class_teacher ect ON exam_class.id = ect.classId " +
                "LEFT JOIN exam_user eu ON ect.teacherId = eu.id " +
                "WHERE exam_class.id = ?";

        return jdbcTemplate.query(sql, new Object[]{classId}, this::mapRowToExamClassdto);
}
    @Override
    public void update(ExamClass examClass) {
        StringBuilder sql = new StringBuilder("UPDATE exam_class SET name = ?");
        List<Object> params = new ArrayList<>();
        params.add(examClass.getName());

        if (examClass.getComment() != null) {
            sql.append(", comment = ?");
            params.add(examClass.getComment());
        }

        if (examClass.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examClass.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examClass.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_class WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }

    }

    private ExamClass mapRowToExamClass(ResultSet rs, int rowNum) throws SQLException {
        return new ExamClass(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("comment"),
                rs.getString("createTime")
        );
    }
    private ExamClassDto mapRowToExamClassdto(ResultSet rs, int rowNum) throws SQLException {
        return new ExamClassDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("comment"),
                rs.getString("teachername"),
                rs.getString("createTime")
        );
    }
}