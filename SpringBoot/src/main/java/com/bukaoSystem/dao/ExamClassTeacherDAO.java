package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.model.ExamClassTeacherDto;

import java.util.List;

public interface ExamClassTeacherDAO {
    List<ExamClassTeacher> getAllExamClassTeachers();
    List<ExamClassTeacher> getExamClassTeacherById(Long teacherId);
    List<ExamClassTeacher> getExamClassTeachersByClassId(Long classId);

    List<ExamClassTeacherDto> getExamClassTeachersnameByClassId(Long classId);

    void saveExamClassTeacher(ExamClassTeacher examClassTeacher);
    void updateExamClassTeacher(ExamClassTeacher examClassTeacher);
    void deleteExamClassTeacher(Long id);
}
