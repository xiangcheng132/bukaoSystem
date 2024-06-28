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

    @GetMapping("/{id}")
    public ExamClass getExamClassById(@PathVariable Long id) {
        return examClassService.getExamClassById(id);
    }

    @PostMapping("/create")
    public void createExamClass(@RequestBody ExamClass examClass) {
        examClassService.saveExamClass(examClass);
    }


    @PostMapping("/update/{id}")
    public void updateExamClass(@PathVariable Long id, @RequestBody ExamClass examClass) {
        examClass.setId(id);
        examClassService.updateExamClass(examClass);
    }

    @PostMapping("/delete/{id}")
    public void deleteExamClass(@PathVariable Long id) {
        examClassService.deleteExamClass(id);
    }
}