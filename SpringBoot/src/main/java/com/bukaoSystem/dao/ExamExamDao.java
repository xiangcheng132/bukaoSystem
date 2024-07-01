package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExam;

import java.util.List;

public interface ExamExamDao {
    List<ExamExam> findAll();
    ExamExam findById(Long id);
    List<ExamExam> findByCourseId(Long id);
    List<ExamExam> findByName(String name);
    void save(ExamExam examExam);
    void update(ExamExam examExam);
    void delete(Long id);
    List<ExamExam> findByStuId(long userId);
    List<ExamExam> findByTeaId(long userId);
    void gradeExams();
}
