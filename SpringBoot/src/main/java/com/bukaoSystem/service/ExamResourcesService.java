package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamResources;

import java.util.List;

public interface ExamResourcesService {
    void saveExamResources(ExamResources examResources);
    void updateExamResources(ExamResources examResources);
    void deleteExamResources(Long id);
    ExamResources getExamResourcesById(Long id);
    List<ExamResources> getExamResourcesByCourseId(Long courseId, String sort);
    List<ExamResources> getExamResourcesByChapterId(Long chapterId, String sort);
}
