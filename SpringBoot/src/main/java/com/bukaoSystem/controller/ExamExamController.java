package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.service.ExamExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/exam")
public class ExamExamController {

    @Autowired
    private ExamExamService examExamService;

    @GetMapping
    public List<ExamExam> getAllExamExams() {
        return examExamService.getAllExamExams();
    }

    @GetMapping("/{id}")
    public ExamExam getExamExamById(@PathVariable Long id) {
        return examExamService.getExamExamById(id);
    }

    @PostMapping("/create")
    public void createExamExam(@RequestBody ExamExam examExam) {
        examExamService.saveExamExam(examExam);
    }

    @PostMapping("/update/{id}")
    public void updateExamExam(@PathVariable Long id, @RequestBody ExamExam examExam) {
        examExam.setId(id);
        examExamService.updateExamExam(examExam);
    }

    @PostMapping("/delete/{id}")
    public void deleteExamExam(@PathVariable Long id) {
        examExamService.deleteExamExam(id);
    }
}
