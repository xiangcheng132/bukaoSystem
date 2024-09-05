package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamClassDto;
import com.bukaoSystem.service.ExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/class")
public class ExamClassController {

    @Autowired
    private ExamClassService examClassService;

    @GetMapping
    public List<ExamClass> getAllExamClasses() {
        return examClassService.getAllExamClasses();
    }
    @GetMapping("/teacher")
    public List<ExamClassDto> getAllExamClasseswithteacher() {
        return examClassService.getAllwithteacher();
    }
    @PostMapping("/getTeacherById")
    public List<ExamClassDto> getAllwithTeacherById(@RequestBody ExamClass examClass) {
        return examClassService.getAllwithTeacherById(examClass.getId());
    }
    @PostMapping("/getById")
    public ExamClass getExamClassById(@RequestBody ExamClass examClass) {
        return examClassService.getExamClassById(examClass.getId());
    }
    @PostMapping("/create")
    public Long createExamClass(@RequestBody ExamClass examClass) {
        return examClassService.saveExamClass(examClass);
    }


    @PostMapping("/update")
    public void updateExamClass(@RequestBody ExamClass examClass) {
        examClassService.updateExamClass(examClass);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteExamClass(@RequestBody ExamClass examClass) {
        try {
            examClassService.deleteExamClass(examClass.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}