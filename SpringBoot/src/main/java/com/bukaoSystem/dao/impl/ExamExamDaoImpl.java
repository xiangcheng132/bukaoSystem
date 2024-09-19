package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamTeacherCourse;
import com.bukaoSystem.service.BatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamExamDaoImpl implements ExamExamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<ExamExam> ExamRowMapper = new RowMapper<ExamExam>(){
        @Override
        public ExamExam mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExamExam exam = new ExamExam();
            exam.setId(rs.getLong("id"));
            exam.setCourseId(rs.getLong("courseId"));
            exam.setName(rs.getString("name"));
            exam.setComment(rs.getString("comment"));
            exam.setPlace(rs.getString("place"));
            exam.setState(rs.getInt("state"));
            exam.setBeginTime(rs.getString("beginTime"));
            exam.setEndTime(rs.getString("endTime"));
            exam.setCreateTime(rs.getString("createTime"));

            // 获取 ResultSet 的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            List<ExamClass> examClasses = new ArrayList<>();

            // 先检查是否存在 classIds 和 classNames 列
            boolean hasClassIds = false;
            boolean hasClassNames = false;

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                if (columnName.equals("classIds")) {
                    hasClassIds = true;
                }
                if (columnName.equals("classNames")) {
                    hasClassNames = true;
                }
            }

            // 如果 classIds 和 classNames 列都存在，则进行处理
            if (hasClassIds && hasClassNames) {
                    String[] classIds = rs.getString("classIds").split(",");
                    String[] classNames = rs.getString("classNames").split(",");

                    int length = Math.min(classIds.length, classNames.length);
                    for (int i = 0; i < length; i++) {
                        ExamClass examClass = new ExamClass();
                        examClass.setId(Long.parseLong(classIds[i]));
                        examClass.setName(classNames[i]);
                        examClasses.add(examClass);
                    }
                }
            exam.setExamClasses(examClasses);

            return exam;
            }
    };


    @Override
    public List<ExamExam> findAll() {
        String sql = "SELECT * FROM exam_exam";
        return jdbcTemplate.query(sql, ExamRowMapper);
    }

    @Override
    public ExamExam findById(Long id) {
        String sql = "SELECT * FROM exam_exam WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id}, ExamRowMapper);
    }

    @Override
    public List<ExamExam> findByCourseId(Long id) {
        String sql = "SELECT * FROM exam_exam WHERE courseId = ?";
        return jdbcTemplate.query(sql,new Object[]{id}, ExamRowMapper);
    }

    @Override
    public List<ExamExam> findByName(String name) {
        String sql = "SELECT * FROM exam_exam WHERE name = ?";
        return jdbcTemplate.query(sql,new Object[]{name}, ExamRowMapper);
    }

    //添加
    @Override
    public void save(ExamExam examExam) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_exam (courseId, name, comment, place, beginTime, endTime");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?, ?, ?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examExam.getCourseId());
        params.add(examExam.getName());
        params.add(examExam.getComment());
        params.add(examExam.getPlace());
        params.add(examExam.getBeginTime());
        params.add(examExam.getEndTime());

        if (examExam.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examExam.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void update(ExamExam examExam) {
        StringBuilder sql = new StringBuilder("UPDATE exam_exam SET courseId = ?, name = ?, comment = ?, place = ?, beginTime = ?, endTime = ?");
        List<Object> params = new ArrayList<>();
        params.add(examExam.getCourseId());
        params.add(examExam.getName());
        params.add(examExam.getComment());
        params.add(examExam.getPlace());
        params.add(examExam.getBeginTime());
        params.add(examExam.getEndTime());

        if (examExam.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examExam.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examExam.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_exam WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }

    @Override
    public List<ExamExam> findByStuId(long userId) {
        String sql = "SELECT DISTINCT  e.* " +
                "FROM exam_user u " +
                "JOIN exam_class_student cs ON u.id = cs.studentId " +
                "JOIN exam_exam_class ec ON cs.classId = ec.classId " +
                "JOIN exam_exam e ON ec.examId = e.id " +
                "WHERE u.id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, ExamRowMapper);
    }

    @Override
    public List<ExamExam> findByTeaId(long userId) {
        String sql = "SELECT e.*, GROUP_CONCAT(DISTINCT ec.id ORDER BY ec.id) as classIds, GROUP_CONCAT(DISTINCT ec.name ORDER BY ec.id) as classNames " +
                "FROM exam_user u " +
                "JOIN exam_teacher_course tc ON u.id = tc.teacherId " +
                "JOIN exam_exam e ON tc.courseId = e.courseId " +
                "JOIN exam_exam_class eec ON e.id = eec.examId " +
                "JOIN exam_class ec ON eec.classId = ec.id " +
                "WHERE u.id = ? " +
                "GROUP BY e.id";

        return jdbcTemplate.query(sql, new Object[]{userId}, ExamRowMapper);
    }


    @Override
    public void gradeExams() {
        String sql = "SELECT a.id as answerId, d.id as detailId, d.userKey as userKey, r.key as correctKey, r.score as score " +
                "FROM exam_exam e " +
                "JOIN exam_answer_sheet a ON e.id = a.examId " +
                "JOIN exam_answer_sheet_detail d ON a.id = d.answerId " +
                "JOIN exam_resources r ON d.resourceId = r.id " +
                "WHERE e.endTime <= ? AND a.isGraded = 0";

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, currentTime);
        if (results.isEmpty()) {
            return;
        }
        Map<Long, Double> answerSheetScores = new HashMap<>(); // answerId -> totalScore
        Map<Long, Integer> answerSheetDetailTrue = new HashMap<>(); // detailId -> isTrue

        for (Map<String, Object> row : results) {
            Long answerId = (Long) row.get("answerId");
            Long detailId = (Long) row.get("detailId");
            String userKey = (String) row.get("userKey");
            String correctKey = (String) row.get("correctKey");
            double score = (Integer) row.get("score");

            int isTrue = userKey.equals(correctKey) ? 1 : 2; // 1 for correct, 2 for incorrect
            answerSheetScores.merge(answerId, isTrue==1?score:0, Double::sum);
            answerSheetDetailTrue.put(detailId, isTrue);
        }

        List<Object[]> updateParamsForAnswerSheet = new ArrayList<>();
        List<Object[]> updateParamsForAnswerSheetDetail = new ArrayList<>();

        for (Long answerId : answerSheetScores.keySet()) {
            double totalScore = answerSheetScores.get(answerId);
            updateParamsForAnswerSheet.add(new Object[]{totalScore, answerId});
        }

        for (Long detailId : answerSheetDetailTrue.keySet()) {
            int isTrue = answerSheetDetailTrue.get(detailId);
            updateParamsForAnswerSheetDetail.add(new Object[]{isTrue, detailId});
        }

        String updateASSql = "UPDATE exam_answer_sheet SET score = ?, isGraded = 1 WHERE id = ?";
        String updateASDSql = "UPDATE exam_answer_sheet_detail SET isTrue = ? WHERE id = ?";
        BatchUpdateService batchUpdateService = new BatchUpdateService();
        batchUpdateService.batchUpdate(jdbcTemplate, updateASSql, updateParamsForAnswerSheet);

        batchUpdateService.batchUpdate(jdbcTemplate, updateASDSql, updateParamsForAnswerSheetDetail);
    }

}





