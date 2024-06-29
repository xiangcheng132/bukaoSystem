package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/resources")
public class ExamResourcesController {

    @Autowired
    private ExamResourcesService examResourcesService;

    @GetMapping
    public List<ExamResources> getAllExamResources() {
        return examResourcesService.getExamResourcesByCourseId(null);
    }

    @GetMapping("/course/{id}")
    public List<ExamResources> getExamResourcesByCourseId(@PathVariable Long id) {
        return examResourcesService.getExamResourcesByCourseId(id);
    }

    @GetMapping("/{id}")
    public ExamResources getExamResourcesById(@PathVariable Long id) {
        return examResourcesService.getExamResourcesById(id);
    }

    @PostMapping("/create")
    public void createExamResources(@RequestBody ExamResources examResources) {
        examResourcesService.saveExamResources(examResources);
    }

    @PostMapping("/update/{id}")
    public void updateExamResources(@PathVariable Long id, @RequestBody ExamResources examResources) {
        examResources.setId(id);
        examResourcesService.updateExamResources(examResources);
    }

    @PostMapping("/delete/{id}")
    public void deleteExamResources(@PathVariable Long id) {
        examResourcesService.deleteExamResources(id);
    }
}
