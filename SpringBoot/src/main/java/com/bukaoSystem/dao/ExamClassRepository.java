package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamClass;
import java.util.List;

public interface ExamClassRepository {
    void save(ExamClass examClass);

    ExamClass findById(Long id);

    List<ExamClass> findAll();

    void update(ExamClass examClass);

    void delete(Long id);
}