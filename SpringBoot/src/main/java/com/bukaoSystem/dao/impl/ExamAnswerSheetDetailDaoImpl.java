package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDetailDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.model.GradingResult;
import com.bukaoSystem.service.BatchUpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamAnswerSheetDetailDaoImpl implements ExamAnswerSheetDetailDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BatchUpdateService batchUpdateService;

    private RowMapper<ExamAnswerSheetDetail> rowMapper = new BeanPropertyRowMapper<>(ExamAnswerSheetDetail.class);

    @Override
    public List<ExamAnswerSheetDetail> getAllExamAnswerSheetDetails() {
        String sql = "SELECT * FROM exam_answer_sheet_detail";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsById(Long id) {
        String sql = "SELECT * FROM exam_answer_sheet_detail WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByAnswerId(Long answerId) {
        String sql = "SELECT easd.*, er.* " +
                "FROM exam_answer_sheet_detail easd " +
                "JOIN exam_resources er ON easd.resourceId = er.id " +
                "WHERE easd.answerId = ?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<ExamAnswerSheetDetail>>() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public List<ExamAnswerSheetDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ExamAnswerSheetDetail> details = new ArrayList<>();
                while (rs.next()) {
                    ExamAnswerSheetDetail detail = new ExamAnswerSheetDetail();
                    detail.setId(rs.getLong("easd.id"));
                    detail.setAnswerId(rs.getLong("easd.answerId"));
                    detail.setResourceId(rs.getLong("easd.resourceId"));
                    detail.setUserKey(rs.getString("easd.userKey"));
                    detail.setIsTrue(rs.getString("easd.isTrue"));
                    detail.setCreateTime(rs.getString("easd.createTime"));

                    ExamResources resources = new ExamResources();
                    resources.setId(rs.getLong("er.id"));
                    resources.setCourseId(rs.getLong("er.courseId"));
                    resources.setChapterId(rs.getLong("er.chapterId"));
                    resources.setQuestion(rs.getString("er.question"));
                    resources.setType(ExamResources.Type.valueOf(rs.getString("er.type")));
                    try {

                        JsonNode optionsNode = objectMapper.readTree(rs.getString("er.options")==null?"":rs.getString("er.options"));
                        resources.setOptions(optionsNode);
                    } catch (JsonProcessingException e) {
                        resources.setOptions(null);
                    }
                    resources.setKey(rs.getString("er.key"));
                    resources.setAnalysis(rs.getString("er.analysis"));
                    resources.setScore(rs.getDouble("er.score"));
                    resources.setCreateTime(rs.getString("er.createTime"));

                    // 将查询到的 ExamResources 设置到 ExamAnswerSheetDetail 中
                    detail.setExamResources(resources);

                    details.add(detail);
                }
                return details;
            }
        }, answerId);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByResourceId(Long resourceId) {
        String sql = "SELECT * FROM exam_answer_sheet_detail WHERE resourceId = ?";
        return jdbcTemplate.query(sql, rowMapper, resourceId);
    }

    @Override
    public void saveExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_answer_sheet_detail (answerId, resourceId, userKey");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");

        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheetDetail.getAnswerId());
        params.add(examAnswerSheetDetail.getResourceId());
        params.add(examAnswerSheetDetail.getUserKey());

        if (examAnswerSheetDetail.getIsTrue() != null) {
            sql.append(", isTrue");
            values.append(", ?");
            params.add(examAnswerSheetDetail.getIsTrue());
        }

        if (examAnswerSheetDetail.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examAnswerSheetDetail.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);
        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void updateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        StringBuilder sql = new StringBuilder("UPDATE exam_answer_sheet_detail SET answerId = ?, resourceId = ?, userKey = ?");
        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheetDetail.getAnswerId());
        params.add(examAnswerSheetDetail.getResourceId());
        params.add(examAnswerSheetDetail.getUserKey());

        if (examAnswerSheetDetail.getIsTrue() != null) {
            sql.append(", isTrue = ?");
            params.add(examAnswerSheetDetail.getIsTrue());
        }

        if (examAnswerSheetDetail.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examAnswerSheetDetail.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examAnswerSheetDetail.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamAnswerSheetDetail(Long id) {
        String sql = "DELETE FROM exam_answer_sheet_detail WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }

    @Override
    public void updateExamAnswerSheetReviewStatus(Long id, String isTrue) {
        String sql2 = "UPDATE exam_answer_sheet_detail SET isTrue = ? WHERE id = ?";
        jdbcTemplate.update(sql2, isTrue, id);
    }

    //答卷展示功能（展示详细的题目和用户答案，标准答案，分值）
    public List<Map<String, Object>> showExams(Long answerId) {
        String sql = "SELECT a.id as answerId, d.id as detailId,d.isTrue as isTrue ,d.userKey as userKey, r.key as correctKey, r.score as score, " +
                "r.question as question, r.type as type, r.options as options, r.analysis as analysis " +
                "FROM exam_answer_sheet a " +
                "JOIN exam_answer_sheet_detail d ON a.id = d.answerId " +
                "JOIN exam_resources r ON d.resourceId = r.id " +
                "WHERE a.id = ? AND a.isGraded = 0";
        return jdbcTemplate.queryForList(sql, answerId);
    }

    public void gradeExamsManually(List<Map<String, Object>> gradingResults) {
        if (gradingResults.isEmpty()) {
            return;
        }
        Map<Long, Double> answerSheetScores = new HashMap<>();
        Map<Long, String> answerSheetDetailTrue = new HashMap<>();

        calculateTotalScores(gradingResults, answerSheetScores, answerSheetDetailTrue);

        List<Object[]> updateParamsForAnswerSheet = new ArrayList<>();
        List<Object[]> updateParamsForAnswerSheetDetail = new ArrayList<>();

        for (Long answerId : answerSheetScores.keySet()) {
            double totalScore = answerSheetScores.get(answerId);
            updateParamsForAnswerSheet.add(new Object[]{totalScore, answerId});
        }
        String updateASSql = "UPDATE exam_answer_sheet SET score = ?, isGraded = 1 WHERE id = ?";
        batchUpdateService.batchUpdate(jdbcTemplate, updateASSql, updateParamsForAnswerSheet);
    }

    private void calculateTotalScores(List<Map<String, Object>> gradingResults, Map<Long, Double> answerSheetScores, Map<Long, String> answerSheetDetailTrue) {
        for (Map<String, Object> row : gradingResults) {
            Long answerId = (Long) row.get("answerId");
            Long detailId = (Long) row.get("detailId");
            String isTrue = (String) row.get("isTrue");
            double score = (Integer) row.get("score");
            int state = 0;
            if (isTrue.equals("对")) {
                state = 1;
            } else if (isTrue.equals("错")) {
                state = 2;
            } else if (isTrue.equals("半对半错")) {
                state = 3;
            }
            switch (state) {
                case 1:
                    answerSheetScores.merge(answerId, score, Double::sum);
                    break;
                case 2:
                    answerSheetScores.merge(answerId, 0.0, Double::sum);
                    break;
                case 3:
                    answerSheetScores.merge(answerId, score / 2, Double::sum);
                    break;
                default:
                    // 处理其他可能的情况
                    answerSheetScores.merge(answerId, 0.0, Double::sum);
                    break;
            }

            answerSheetDetailTrue.put(detailId, isTrue);
        }
    }
}

