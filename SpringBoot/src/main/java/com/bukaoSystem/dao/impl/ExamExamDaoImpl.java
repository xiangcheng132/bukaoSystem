package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamDao;
import com.bukaoSystem.model.ExamExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExamExamDaoImpl implements ExamExamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ExamExam> findAll() {
        String sql = "SELECT * FROM exam_exam";
        return jdbcTemplate.query(sql, new ExamExamRowMapper());
    }

    @Override
    public ExamExam findById(Long id) {
        String sql = "SELECT * FROM exam_exam WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ExamExamRowMapper(), id);
    }

    @Override
    public List<ExamExam> findByCourseId(Long id) {
        String sql = "SELECT * FROM exam_exam WHERE courseId = ?";
        return jdbcTemplate.query(sql, new ExamExamRowMapper(),id);
    }

    @Override
    public List<ExamExam> findByName(String name) {
        String sql = "SELECT * FROM exam_exam WHERE name = ?";
        return jdbcTemplate.query(sql, new ExamExamRowMapper(),name);
    }


    @Override
    public void save(ExamExam examExam) {
        String sql = "INSERT INTO exam_exam (courseId, name, comment, place, beginTime, endTime, createTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                examExam.getCourseId(),
                examExam.getName(),
                examExam.getComment(),
                examExam.getPlace(),
                examExam.getBeginTime(),
                examExam.getEndTime(),
                examExam.getCreateTime());
    }

    @Override
    public void update(ExamExam examExam) {
        String sql = "UPDATE exam_exam SET courseId = ?, name = ?, comment = ?, place = ?, beginTime = ?, endTime = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                examExam.getCourseId(),
                examExam.getName(),
                examExam.getComment(),
                examExam.getPlace(),
                examExam.getBeginTime(),
                examExam.getEndTime(),
                examExam.getCreateTime(),
                examExam.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_exam WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ExamExamRowMapper implements RowMapper<ExamExam> {
        @Override
        public ExamExam mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExamExam examExam = new ExamExam();
            examExam.setId(rs.getLong("id"));
            examExam.setCourseId(rs.getLong("courseId"));
            examExam.setName(rs.getString("name"));
            examExam.setComment(rs.getString("comment"));
            examExam.setPlace(rs.getString("place"));
            examExam.setBeginTime(rs.getString("beginTime"));
            examExam.setEndTime(rs.getString("endTime"));
            examExam.setCreateTime(rs.getString("createTime"));
            return examExam;
        }
    }
}
