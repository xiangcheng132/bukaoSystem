package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamClassStudentDao;
import com.bukaoSystem.model.ExamClassStudent;
import com.bukaoSystem.model.ExamClassStudentDto;
import com.bukaoSystem.service.ExamClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamClassStudentServiceImpl implements ExamClassStudentService {

    @Autowired
    private ExamClassStudentDao examClassStudentDao;

    @Override
    public List<ExamClassStudent> getAllExamClassStudents() {
        return examClassStudentDao.getAllExamClassStudents();
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsById(Long id) {
        return examClassStudentDao.getExamClassStudentsById(id);
    }
    @Override
    public List<ExamClassStudentDto> getClassStudentsById(Long classId) {
        return examClassStudentDao.getClassStudentsById(classId);
    }
    @Override
    public List<ExamClassStudent> getExamClassStudentsByClassId(Long classId) {
        return examClassStudentDao.getExamClassStudentsByClassId(classId);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsByStudentId(Long studentId) {
        return examClassStudentDao.getExamClassStudentsByStudentId(studentId);
    }

    @Override
    public void saveExamClassStudent(ExamClassStudent examClassStudent) {
        examClassStudentDao.saveExamClassStudent(examClassStudent);
    }

    @Override
    public void updateExamClassStudent(ExamClassStudent examClassStudent) {
        examClassStudentDao.updateExamClassStudent(examClassStudent);
    }

    @Override
    public void deleteExamClassStudent(Long id) {
        examClassStudentDao.deleteExamClassStudent(id);
    }
}
