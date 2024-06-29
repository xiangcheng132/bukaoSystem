package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExam;

import java.util.List;

public interface ExamExamDao {
    List<ExamExam> findAll();
    ExamExam findById(Long id);
    void save(ExamExam examExam);
    void update(ExamExam examExam);
    void delete(Long id);
}
