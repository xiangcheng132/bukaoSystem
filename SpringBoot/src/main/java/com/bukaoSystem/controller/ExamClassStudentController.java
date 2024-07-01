package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClassStudent;
import com.bukaoSystem.service.ExamClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/getById")
    public List<ExamClassStudent> getExamClassStudentsById(@RequestBody ExamClassStudent examClassStudent) {
        return examClassStudentService.getExamClassStudentsById(examClassStudent.getId());
    }

    @PostMapping("/getByClassId")
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
    public ResponseEntity<String> deleteExamClassStudent(@RequestBody ExamClassStudent examClassStudent) {
        try {
            examClassStudentService.deleteExamClassStudent(examClassStudent.getId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
