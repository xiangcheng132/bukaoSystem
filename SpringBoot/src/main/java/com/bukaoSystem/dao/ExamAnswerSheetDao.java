package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamAnswerSheet;
import java.util.List;

public interface ExamAnswerSheetDao {
    List<ExamAnswerSheet> getAllExamAnswerSheets();
    List<ExamAnswerSheet> getExamAnswerSheetsById(Long id);
    List<ExamAnswerSheet> getExamAnswerSheetsByExamId(Long examId);
    List<ExamAnswerSheet> getExamAnswerSheetsByUserId(Long userId);
    Long saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
    void updateExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
    void deleteExamAnswerSheet(Long id);
    Long saveOrUpdateExamAnswerSheet(ExamAnswerSheet examAnswerSheet);
    List<ExamAnswerSheet> findAnswerSheetsByTeacherId(Long teacherId);
}
