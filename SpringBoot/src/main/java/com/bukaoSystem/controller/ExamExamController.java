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

    @PostMapping("/getById")
    public ExamExam getExamExamById(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamById(examExam.getId());
    }

    @PostMapping("/getByCourseId")
    public List<ExamExam> getExamExamByCourseId(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamsByCourseId(examExam.getCourseId());
    }

    @PostMapping("/getByName")
    public List<ExamExam> getExamExamByName(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamsByName(examExam.getName());
    }

    @PostMapping("/create")
    public void createExamExam(@RequestBody ExamExam examExam) {
        examExamService.saveExamExam(examExam);
    }

    @PostMapping("/update")
    public void updateExamExam(@RequestBody ExamExam examExam) {
        examExamService.updateExamExam(examExam);
    }

    @PostMapping("/delete")
    public void deleteExamExam(@RequestBody ExamExam examExam) {
        examExamService.deleteExamExam(examExam.getId());
    }
}
