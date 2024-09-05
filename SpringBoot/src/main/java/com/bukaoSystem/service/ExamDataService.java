package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamDataDto;

import java.util.Map;

public interface ExamDataService {
    ExamDataDto getData();

    Map<String, Integer> getMonthlyUserCounts();
}
