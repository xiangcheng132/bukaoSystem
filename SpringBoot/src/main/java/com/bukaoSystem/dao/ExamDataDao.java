package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamDataDto;

import java.util.Map;

public interface ExamDataDao {
    ExamDataDto getExamData();

    Map<String, Integer> getMonthlyUserCounts();
}
