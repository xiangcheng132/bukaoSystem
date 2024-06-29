package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExamClass;
import java.util.List;

public interface ExamExamClassDao {
    List<ExamExamClass> getAllExamExamClasses();
    List<ExamExamClass> getExamExamClassesByExamId(Long examId);
    List<ExamExamClass> getExamExamClassesById(Long id);
    List<ExamExamClass> getExamExamClassesByClassId(Long classId);
    void saveExamExamClass(ExamExamClass examExamClass);
    void updateExamExamClass(ExamExamClass examExamClass);
    void deleteExamExamClass(Long examId, Long classId);
}
