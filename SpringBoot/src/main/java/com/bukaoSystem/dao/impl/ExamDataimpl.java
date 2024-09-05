package com.bukaoSystem.dao.impl;

import com.bukaoSystem.model.ExamDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamDataimpl implements com.bukaoSystem.dao.ExamDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ExamDataDto getExamData() {
        ExamDataDto examDataDto = new ExamDataDto();

        String examPaperCountSql = "SELECT COUNT(*) FROM exam_exam";
        String questionCountSql = "SELECT COUNT(*) FROM exam_resources";
        String doExamPaperCountSql = "SELECT COUNT(*) FROM exam_answer_sheet";
        String doQuestionCountSql = "SELECT COUNT(*) FROM exam_answer_sheet_detail";

        examDataDto.setExamPaperCount(jdbcTemplate.queryForObject(examPaperCountSql, Long.class));
        examDataDto.setQuestionCount(jdbcTemplate.queryForObject(questionCountSql, Long.class));
        examDataDto.setDoExamPaperCount(jdbcTemplate.queryForObject(doExamPaperCountSql, Long.class));
        examDataDto.setDoQuestionCount(jdbcTemplate.queryForObject(doQuestionCountSql, Long.class));

        return examDataDto;
    }
    private static final String[] MONTHS_ENGLISH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public Map<String, Integer> getMonthlyUserCounts() {
        int currentYear = Year.now().getValue();

        String sql = "SELECT months.month AS month, " +
                "COALESCE(COUNT(u.id), 0) AS count " +
                "FROM ( " +
                "    SELECT 1 AS month UNION ALL " +
                "    SELECT 2 UNION ALL " +
                "    SELECT 3 UNION ALL " +
                "    SELECT 4 UNION ALL " +
                "    SELECT 5 UNION ALL " +
                "    SELECT 6 UNION ALL " +
                "    SELECT 7 UNION ALL " +
                "    SELECT 8 UNION ALL " +
                "    SELECT 9 UNION ALL " +
                "    SELECT 10 UNION ALL " +
                "    SELECT 11 UNION ALL " +
                "    SELECT 12 " +
                ") AS months " +
                "LEFT JOIN exam_user u " +
                "ON months.month = MONTH(u.createTime) " +
                "AND YEAR(u.createTime) = ? " +
                "GROUP BY months.month " +
                "ORDER BY months.month";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, currentYear);

        Map<String, Integer> monthlyCounts = new HashMap<>();
        for (Map<String, Object> result : results) {
            Integer month = ((Number) result.get("month")).intValue();
            String monthName = MONTHS_ENGLISH[month - 1]; // 获取对应的英文简写
            Integer count = ((Number) result.get("count")).intValue();
            monthlyCounts.put(monthName, count);
        }

        return monthlyCounts;
    }
}

