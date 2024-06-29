package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamClassStudent;
import java.util.List;

public interface ExamClassStudentDao {
    List<ExamClassStudent> getAllExamClassStudents();
    List<ExamClassStudent> getExamClassStudentsById(Long id);
    List<ExamClassStudent> getExamClassStudentsByClassId(Long classId);
    List<ExamClassStudent> getExamClassStudentsByStudentId(Long studentId);
    void saveExamClassStudent(ExamClassStudent examClassStudent);
    void updateExamClassStudent(ExamClassStudent examClassStudent);
    void deleteExamClassStudent(Long id);
}
