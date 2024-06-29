package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamExamClass;
import java.util.List;

public interface ExamExamClassService {
    List<ExamExamClass> getAllExamExamClasses();
    List<ExamExamClass> getExamExamClassesByExamId(Long examId);
    List<ExamExamClass> getExamExamClassesByClassId(Long classId);
    void saveExamExamClass(ExamExamClass examExamClass);
    void updateExamExamClass(ExamExamClass examExamClass);
    void deleteExamExamClass(Long examId, Long classId);
}
