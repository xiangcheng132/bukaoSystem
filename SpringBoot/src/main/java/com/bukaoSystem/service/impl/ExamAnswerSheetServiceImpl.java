package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDao;
import com.bukaoSystem.model.ExamAnswerSheet;
import com.bukaoSystem.service.ExamAnswerSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamAnswerSheetServiceImpl implements ExamAnswerSheetService {

    @Autowired
    private ExamAnswerSheetDao examAnswerSheetDao;

    @Override
    public List<ExamAnswerSheet> getAllExamAnswerSheets() {
        return examAnswerSheetDao.getAllExamAnswerSheets();
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsById(Long id) {
        return examAnswerSheetDao.getExamAnswerSheetsById(id);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsByExamId(Long examId) {
        return examAnswerSheetDao.getExamAnswerSheetsByExamId(examId);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsByUserId(Long userId) {
        return examAnswerSheetDao.getExamAnswerSheetsByUserId(userId);
    }

    @Override
    public void saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetDao.saveExamAnswerSheet(examAnswerSheet);
    }

    @Override
    public void updateExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetDao.updateExamAnswerSheet(examAnswerSheet);
    }

    @Override
    public void deleteExamAnswerSheet(Long id) {
        examAnswerSheetDao.deleteExamAnswerSheet(id);
    }
    @Override
    public void saveOrUpdateExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetDao.saveOrUpdateExamAnswerSheet(examAnswerSheet);
    }
}
