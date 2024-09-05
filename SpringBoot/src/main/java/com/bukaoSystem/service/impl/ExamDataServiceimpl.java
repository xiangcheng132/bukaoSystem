package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamDataDao;
import com.bukaoSystem.model.ExamDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamDataServiceimpl implements com.bukaoSystem.service.ExamDataService {
    @Autowired
    private ExamDataDao ExamData;
    @Override
    public ExamDataDto getData() {
        return ExamData.getExamData();
    }
    @Override
    public Map<String, Integer> getMonthlyUserCounts() {
        return ExamData.getMonthlyUserCounts();
    }
}
