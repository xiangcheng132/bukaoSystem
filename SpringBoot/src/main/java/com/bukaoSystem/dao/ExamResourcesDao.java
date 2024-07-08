package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamResources;

import java.util.List;

public interface ExamResourcesDao {
    void save(ExamResources examResources);
    void update(ExamResources examResources);
    void delete(Long id);
    ExamResources findById(Long id);
    List<ExamResources> findByCourseId(Long courseId, String sort);
    List<ExamResources> findByChapterId(Long chapterId, String sort);
    List<ExamResources> findRandomResourcesByChapterAndType(Long chapterId, String questionType, int count);
}
