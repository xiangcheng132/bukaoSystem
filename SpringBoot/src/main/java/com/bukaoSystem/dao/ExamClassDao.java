package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamClassDto;

import java.util.List;

public interface ExamClassDao {
    Long save(ExamClass examClass);

    ExamClass findById(Long id);

    List<ExamClass> findAll();

    List<ExamClassDto> findAllwithteacher();

    List<ExamClassDto> findAllwithTeacherById(Long classId);

    void update(ExamClass examClass);

    void delete(Long id);
}