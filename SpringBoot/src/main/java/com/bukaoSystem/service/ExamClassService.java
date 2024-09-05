package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamClassDto;

import java.util.List;

public interface ExamClassService {
    Long saveExamClass(ExamClass examClass);

    ExamClass getExamClassById(Long id);

    List<ExamClass> getAllExamClasses();

    List<ExamClassDto> getAllwithteacher();

    List<ExamClassDto> getAllwithTeacherById(Long classId);

    void updateExamClass(ExamClass examClass);

    void deleteExamClass(Long id);

}