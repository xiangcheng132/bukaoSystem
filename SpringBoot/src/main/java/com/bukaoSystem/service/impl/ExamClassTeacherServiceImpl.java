package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamClassTeacherDAO;
import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.service.ExamClassTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamClassTeacherServiceImpl implements ExamClassTeacherService {

    @Autowired
    private ExamClassTeacherDAO examClassTeacherDAO;

    @Override
    public List<ExamClassTeacher> getAllExamClassTeachers() {
        return examClassTeacherDAO.getAllExamClassTeachers();
    }

    @Override
    public ExamClassTeacher getExamClassTeacherById(Long id) {
        return examClassTeacherDAO.getExamClassTeacherById(id);
    }

    @Override
    public List<ExamClassTeacher> getExamClassTeachersByClassId(Long classId) {
        return examClassTeacherDAO.getExamClassTeachersByClassId(classId);
    }

    @Override
    public void saveExamClassTeacher(ExamClassTeacher examClassTeacher) {
        examClassTeacherDAO.saveExamClassTeacher(examClassTeacher);
    }

    @Override
    public void updateExamClassTeacher(ExamClassTeacher examClassTeacher) {
        examClassTeacherDAO.updateExamClassTeacher(examClassTeacher);
    }

    @Override
    public void deleteExamClassTeacher(Long id) {
        examClassTeacherDAO.deleteExamClassTeacher(id);
    }
}
