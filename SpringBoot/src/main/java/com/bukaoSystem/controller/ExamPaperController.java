package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamPaperDto;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/exampaper")
public class ExamPaperController {

    @Autowired
    private ExamPaperService examPaperService;

    @PostMapping
    public int createExamPaper(@RequestBody ExamPaperDto examPaperDto) {
        return examPaperService.createExamPaper(examPaperDto);
    }
    @PostMapping("/save")
    public Long ExamPaper(@RequestBody ExamPaperDto examPaperDto) {
        return examPaperService.saveexam(examPaperDto);
    }
}

