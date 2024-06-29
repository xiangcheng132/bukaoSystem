package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamClassTeacher;

import java.util.List;

public interface ExamClassTeacherDAO {
    List<ExamClassTeacher> getAllExamClassTeachers();
    ExamClassTeacher getExamClassTeacherById(Long id);
    List<ExamClassTeacher> getExamClassTeachersByClassId(Long classId);
    void saveExamClassTeacher(ExamClassTeacher examClassTeacher);
    void updateExamClassTeacher(ExamClassTeacher examClassTeacher);
    void deleteExamClassTeacher(Long id);
}
