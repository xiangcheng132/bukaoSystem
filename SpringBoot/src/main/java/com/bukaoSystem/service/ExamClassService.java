package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamClass;

import java.util.List;

public interface ExamClassService {
    void saveExamClass(ExamClass examClass);

    ExamClass getExamClassById(Long id);

    List<ExamClass> getAllExamClasses();

    void updateExamClass(ExamClass examClass);

    void deleteExamClass(Long id);
}