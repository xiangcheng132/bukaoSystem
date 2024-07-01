package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamExamClass;
import com.bukaoSystem.service.ExamExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/examClass")
public class ExamExamClassController {

    @Autowired
    private ExamExamClassService examExamClassService;

    @GetMapping
    public List<ExamExamClass> getAllExamExamClasses() {
        return examExamClassService.getAllExamExamClasses();
    }
    @PostMapping("/getById")
    public List<ExamExamClass> getExamExamClassesById(@RequestBody ExamExamClass examExamClass) {
        return examExamClassService.getExamExamClassesById(examExamClass.getId());
    }

    @PostMapping("/getByExamId")
    public List<ExamExamClass> getExamExamClassesByExamId(@RequestBody ExamExamClass examExamClass) {
        return examExamClassService.getExamExamClassesByExamId(examExamClass.getExamId());
    }

    @PostMapping("/getByClassId")
    public List<ExamExamClass> getExamExamClassesByClassId(@RequestBody ExamExamClass examExamClass) {
        return examExamClassService.getExamExamClassesByClassId(examExamClass.getClassId());
    }

    @PostMapping("/create")
    public void createExamExamClass(@RequestBody ExamExamClass examExamClass) {
        examExamClassService.saveExamExamClass(examExamClass);
    }

    @PostMapping("/update")
    public void updateExamExamClass(@RequestBody ExamExamClass examExamClass) {
        examExamClassService.updateExamExamClass(examExamClass);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteExamExamClass(@RequestBody ExamExamClass examExamClass) {
        try {
            examExamClassService.deleteExamExamClass(examExamClass.getExamId(), examExamClass.getClassId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
