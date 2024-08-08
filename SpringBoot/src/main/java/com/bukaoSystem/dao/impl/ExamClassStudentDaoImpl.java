package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassStudentDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClassStudent;
import com.bukaoSystem.model.ExamClassStudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamClassStudentDaoImpl implements ExamClassStudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamClassStudent> rowMapper = new BeanPropertyRowMapper<>(ExamClassStudent.class);
    private RowMapper<ExamClassStudentDto> rowMapper1 = new BeanPropertyRowMapper<>(ExamClassStudentDto.class);
    @Override
    public List<ExamClassStudent> getAllExamClassStudents() {
        String sql = "SELECT * FROM exam_class_student";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsById(Long id) {
        String sql = "SELECT * FROM exam_class_student WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }
    @Override
    public List<ExamClassStudentDto> getClassStudentsById(Long classId) {
        String sql = "SELECT ecs.*, eu.username FROM exam_class_student ecs " +
                "left join exam_user eu ON ecs.studentId = eu.id " +
                "WHERE ecs.classId = ?";
        return jdbcTemplate.query(sql, rowMapper1, classId);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsByClassId(Long classId) {
        String sql = "SELECT * FROM exam_class_student WHERE classId = ?";
        return jdbcTemplate.query(sql, rowMapper, classId);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsByStudentId(Long studentId) {
        String sql = "SELECT * FROM exam_class_student WHERE studentId = ?";
        return jdbcTemplate.query(sql, rowMapper, studentId);
    }

    @Override
    public void saveExamClassStudent(ExamClassStudent examClassStudent) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_class_student (classId, studentId");
        StringBuilder values = new StringBuilder(" VALUES (?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examClassStudent.getClassId());
        params.add(examClassStudent.getStudentId());

        if (examClassStudent.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examClassStudent.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void updateExamClassStudent(ExamClassStudent examClassStudent) {
        StringBuilder sql = new StringBuilder("UPDATE exam_class_student SET classId = ?, studentId = ?");
        List<Object> params = new ArrayList<>();
        params.add(examClassStudent.getClassId());
        params.add(examClassStudent.getStudentId());

        if (examClassStudent.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examClassStudent.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examClassStudent.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamClassStudent(Long id) {
        String sql = "DELETE FROM exam_class_student WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
}
