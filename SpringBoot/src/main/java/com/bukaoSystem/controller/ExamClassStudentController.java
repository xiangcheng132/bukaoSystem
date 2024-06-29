package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamClassStudent;
import com.bukaoSystem.service.ExamClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/classStudent")
public class ExamClassStudentController {

    @Autowired
    private ExamClassStudentService examClassStudentService;

    @GetMapping
    public List<ExamClassStudent> getAllExamClassStudents() {
        return examClassStudentService.getAllExamClassStudents();
    }

    @GetMapping("/getById")
    public List<ExamClassStudent> getExamClassStudentsById(@RequestBody ExamClassStudent examClassStudent) {
        return examClassStudentService.getExamClassStudentsById(examClassStudent.getId());
    }

    @GetMapping("/getByClassId")
    public List<ExamClassStudent> getExamClassStudentsByClassId(@RequestBody ExamClassStudent examClassStudent) {
        return examClassStudentService.getExamClassStudentsByClassId(examClassStudent.getClassId());
    }

    @GetMapping("/getByStudentId")
    public List<ExamClassStudent> getExamClassStudentsByStudentId(@RequestBody ExamClassStudent examClassStudent) {
        return examClassStudentService.getExamClassStudentsByStudentId(examClassStudent.getStudentId());
    }

    @PostMapping("/create")
    public void createExamClassStudent(@RequestBody ExamClassStudent examClassStudent) {
        examClassStudentService.saveExamClassStudent(examClassStudent);
    }

    @PostMapping("/update")
    public void updateExamClassStudent(@RequestBody ExamClassStudent examClassStudent) {
        examClassStudentService.updateExamClassStudent(examClassStudent);
    }

    @PostMapping("/delete")
    public void deleteExamClassStudent(@RequestBody ExamClassStudent examClassStudent) {
        examClassStudentService.deleteExamClassStudent(examClassStudent.getId());
    }
}
