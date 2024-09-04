package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExamResources;
import java.util.List;
import java.util.Map;

public interface ExamExamResourcesDao {
    List<ExamExamResources> getAllExamExamResources();
    List<ExamExamResources> getExamExamResourcesByExamId(Long examId);
    List<ExamExamResources> getExamExamResourcesById(Long Id);
    List<ExamExamResources> getExamExamResourcesByResourceId(Long resourceId);
    void saveExamExamResources(ExamExamResources examExamResources);
    void updateExamExamResources(ExamExamResources examExamResources);
    void deleteExamExamResources(Long examId, Long resourceId);
    List<Map<String, Object>> getExamResourcesByExamId(Long examId);
}
