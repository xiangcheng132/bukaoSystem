package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.service.ExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/getById")
    public ExamClass getExamClassById(@RequestBody ExamClass examClass) {
        return examClassService.getExamClassById(examClass.getId());
    }

    @PostMapping("/create")
    public void createExamClass(@RequestBody ExamClass examClass) {
        examClassService.saveExamClass(examClass);
    }


    @PostMapping("/update")
    public void updateExamClass(@RequestBody ExamClass examClass) {
        examClassService.updateExamClass(examClass);
    }

    @PostMapping("/delete")
    public void deleteExamClass(@RequestBody ExamClass examClass) {
        examClassService.deleteExamClass(examClass.getId());
    }
}