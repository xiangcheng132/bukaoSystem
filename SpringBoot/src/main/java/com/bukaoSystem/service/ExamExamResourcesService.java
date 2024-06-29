package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamExamResources;
import java.util.List;

public interface ExamExamResourcesService {
    List<ExamExamResources> getAllExamExamResources();
    List<ExamExamResources> getExamExamResourcesByExamId(Long examId);
    List<ExamExamResources> getExamExamResourcesById(Long Id);

    List<ExamExamResources> getExamExamResourcesByResourceId(Long resourceId);
    void saveExamExamResources(ExamExamResources examExamResources);
    void updateExamExamResources(ExamExamResources examExamResources);
    void deleteExamExamResources(Long examId, Long resourceId);
}
