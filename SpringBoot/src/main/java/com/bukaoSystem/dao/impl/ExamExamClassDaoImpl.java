package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamClassDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamExamClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamExamClassDaoImpl implements ExamExamClassDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamExamClass> rowMapper = new BeanPropertyRowMapper<>(ExamExamClass.class);

    @Override
    public List<ExamExamClass> getAllExamExamClasses() {
        String sql = "SELECT * FROM exam_exam_class";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamExamClass> getExamExamClassesByExamId(Long examId) {
        String sql = "SELECT * FROM exam_exam_class WHERE examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }

    @Override
    public List<ExamExamClass> getExamExamClassesById(Long id) {
        String sql = "SELECT * FROM exam_exam_class WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamExamClass> getExamExamClassesByClassId(Long classId) {
        String sql = "SELECT * FROM exam_exam_class WHERE classId = ?";
        return jdbcTemplate.query(sql, rowMapper, classId);
    }

    @Override
    public void saveExamExamClass(ExamExamClass examExamClass) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_exam_class (examId, classId");
        StringBuilder values = new StringBuilder(" VALUES (?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examExamClass.getExamId());
        params.add(examExamClass.getClassId());

        if (examExamClass.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examExamClass.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void updateExamExamClass(ExamExamClass examExamClass) {
        StringBuilder sql = new StringBuilder("UPDATE exam_exam_class SET");
        List<Object> params = new ArrayList<>();

        if (examExamClass.getCreateTime() != null) {
            sql.append(" createTime = ?");
            params.add(examExamClass.getCreateTime());
        } else {
            // 如果 createTime 是 null，则可以选择在这里处理，或者忽略该字段的更新
            // 在此示例中，我们选择忽略该字段的更新
        }

        sql.append(" WHERE examId = ? AND classId = ?");
        params.add(examExamClass.getExamId());
        params.add(examExamClass.getClassId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamExamClass(Long examId, Long classId) {
        String sql = "DELETE FROM exam_exam_class WHERE examId = ? AND classId = ?";

        try {
            jdbcTemplate.update(sql, examId, classId);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除examId: " + examId + "classId: " + classId + "的信息，该id下有关联信息。");
        }
    }
}
