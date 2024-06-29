package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamExam;

import java.util.List;

public interface ExamExamService {
    List<ExamExam> getAllExamExams();
    ExamExam getExamExamById(Long id);
    void saveExamExam(ExamExam examExam);
    void updateExamExam(ExamExam examExam);
    void deleteExamExam(Long id);
}
