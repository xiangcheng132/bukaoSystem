package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamAnswerSheet;
import com.bukaoSystem.service.ExamAnswerSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/answerSheet")
public class ExamAnswerSheetController {

    @Autowired
    private ExamAnswerSheetService examAnswerSheetService;

    @GetMapping
    public List<ExamAnswerSheet> getAllExamAnswerSheets() {
        return examAnswerSheetService.getAllExamAnswerSheets();
    }

    @PostMapping("/getById")
    public List<ExamAnswerSheet> getExamAnswerSheetsById(@RequestBody ExamAnswerSheet examAnswerSheet) {
        return examAnswerSheetService.getExamAnswerSheetsById(examAnswerSheet.getId());
    }

    @PostMapping("/getByExamId")
    public List<ExamAnswerSheet> getExamAnswerSheetsByExamId(@RequestBody ExamAnswerSheet examAnswerSheet) {
        return examAnswerSheetService.getExamAnswerSheetsByExamId(examAnswerSheet.getExamId());
    }

    @PostMapping("/getByUserId")
    public List<ExamAnswerSheet> getExamAnswerSheetsByUserId(@RequestBody ExamAnswerSheet examAnswerSheet) {
        return examAnswerSheetService.getExamAnswerSheetsByUserId(examAnswerSheet.getUserId());
    }

    @PostMapping("/create")
    public void createExamAnswerSheet(@RequestBody ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetService.saveExamAnswerSheet(examAnswerSheet);
    }

    @PostMapping("/update")
    public void updateExamAnswerSheet(@RequestBody ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetService.updateExamAnswerSheet(examAnswerSheet);
    }

    @PostMapping("/delete")
    public void deleteExamAnswerSheet(@RequestBody ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetService.deleteExamAnswerSheet(examAnswerSheet.getId());
    }
}
