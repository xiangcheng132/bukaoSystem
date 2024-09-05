package com.bukaoSystem.controller;
import com.bukaoSystem.model.ExamDataDto;
import com.bukaoSystem.service.ExamDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bukaoSystem/data")
public class ExamDataController {

    @Autowired
    private ExamDataService examDataService;

    @GetMapping
    public ExamDataDto getExamData() {
        return examDataService.getData();
    }
    @PostMapping("/getdata")
    public Map<String, Integer> getMonthlyUserCounts() {
        return examDataService.getMonthlyUserCounts();
    }
}