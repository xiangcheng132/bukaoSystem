package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamAnswerSheet;
import java.util.List;

public interface ExamAnswerSheetDao {
    List<ExamAnswerSheet> getAllExamAnswerSheets();
    List<ExamAnswerSheet> getExamAnswerSheetsById(Long id);
    List<ExamAnswerSheet> getExamAnswerSheetsByExamId(Long examId);
    List<ExamAnswerSheet> getExamAnswerSheetsByUserId(Long userId);
    void saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
    void updateExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
    void deleteExamAnswerSheet(Long id);
    void saveOrUpdateExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
}
