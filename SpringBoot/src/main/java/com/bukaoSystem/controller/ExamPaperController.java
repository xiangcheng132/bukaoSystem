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
    public ResponseEntity<ExamExam> createExamPaper(@RequestBody ExamPaperDto examPaperDto) {
        ExamExam examPaper = new ExamExam();
        // 从 DTO 中设置 examPaper 的属性
        examPaper.setCourseId(examPaperDto.getCourseId());
        examPaper.setName(examPaperDto.getName());
        examPaper.setComment(examPaperDto.getComment());
        examPaper.setPlace(examPaperDto.getPlace());
        examPaper.setState(examPaperDto.getState());
        examPaper.setBeginTime(examPaperDto.getBeginTime());
        examPaper.setEndTime(examPaperDto.getEndTime());
        examPaper.setCreateTime(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
        List<Long> resourceIds = examPaperDto.getResourceIds();
        ExamExam createdExamPaper = examPaperService.createExamPaper(examPaper, resourceIds);
        return new ResponseEntity<>(createdExamPaper, HttpStatus.CREATED);
    }
    //得到相应试题
    @GetMapping("/resources")
    public ResponseEntity<List<ExamResources>> getRandomResources(@RequestBody ExamPaperDto examPaperDto) {
        List<ExamResources> resources = examPaperService.getResources(
                examPaperDto.getCourseId(),
                examPaperDto.getChapterIds(),
                examPaperDto.getType(),
                examPaperDto.getLimit(),
                examPaperDto.getScore()
        );
//        List<ExamResources> resources = examPaperService.getRandomResources(chapterId, question, limit);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}

