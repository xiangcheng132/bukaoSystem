package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.service.ExamClassTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/classTeacher")
public class ExamClassTeacherController {

    @Autowired
    private ExamClassTeacherService examClassTeacherService;

    @GetMapping
    public List<ExamClassTeacher> getAllExamClassTeachers() {
        return examClassTeacherService.getAllExamClassTeachers();
    }

    @PostMapping("/getById")
    public ExamClassTeacher getExamClassTeacherById(@RequestBody ExamClassTeacher examClassTeacher) {
        return examClassTeacherService.getExamClassTeacherById(examClassTeacher.getId());
    }

    @PostMapping("/getByClassId")
    public List<ExamClassTeacher> getExamClassTeachersByClassId(@RequestBody ExamClassTeacher examClassTeacher) {
        return examClassTeacherService.getExamClassTeachersByClassId(examClassTeacher.getClassId());
    }

    @PostMapping("/create")
    public void createExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        examClassTeacherService.saveExamClassTeacher(examClassTeacher);
    }

    @PostMapping("/update")
    public void updateExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        examClassTeacherService.updateExamClassTeacher(examClassTeacher);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        try {
            examClassTeacherService.deleteExamClassTeacher(examClassTeacher.getId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
