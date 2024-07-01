package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamExam;

import java.util.List;

public interface ExamExamService {
    List<ExamExam> getAllExamExams();
    ExamExam getExamExamById(Long id);

    List<ExamExam> getExamExamsByCourseId(Long id);

    List<ExamExam> getExamExamsByName(String name);
    void saveExamExam(ExamExam examExam);
    void updateExamExam(ExamExam examExam);
    void deleteExamExam(Long id);
    List<ExamExam> getExamExamsByStuId(Long userId);
    List<ExamExam> getExamExamsByTeaId(Long userId);
    void gradeExams();
}
