package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheet;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamAnswerSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> deleteExamAnswerSheet(@RequestBody ExamAnswerSheet examAnswerSheet) {
        try {
            examAnswerSheetService.deleteExamAnswerSheet(examAnswerSheet.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdateupdateExamAnswerSheet(@RequestBody ExamAnswerSheet examAnswerSheet) {
        examAnswerSheetService.saveOrUpdateExamAnswerSheet(examAnswerSheet);
    }
    @PostMapping("/getByTeacherId")
    public List<ExamAnswerSheet> getAnswerSheetsByTeacherId(@RequestBody ExamUser user) {
        return examAnswerSheetService.getAnswerSheetsByTeacherId(user.getId());
    }
}
