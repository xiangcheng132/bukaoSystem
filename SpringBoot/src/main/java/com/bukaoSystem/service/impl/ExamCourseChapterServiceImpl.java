package com.bukaoSystem.service.impl;


import com.bukaoSystem.dao.ExamCourseChapterDao;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseChapter;
import com.bukaoSystem.service.ExamCourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamCourseChapterServiceImpl implements ExamCourseChapterService {

    @Autowired
    private ExamCourseChapterDao examCourseChapterDao;

    @Override
    public void saveExamCourseChapter(ExamCourseChapter examCourseChapter) {
        examCourseChapterDao.saveExamCourseChapter(examCourseChapter);
    }

    @Override
    public void updateExamCourseChapter(ExamCourseChapter examCourseChapter) {
        examCourseChapterDao.updateExamCourseChapter(examCourseChapter);
    }

    @Override
    public void deleteExamCourseChapter(Long id) {
        examCourseChapterDao.deleteExamCourseChapter(id);
    }

    @Override
    public ExamCourseChapter getExamCourseChapterById(Long id) {
        return examCourseChapterDao.getExamCourseChapterById(id);
    }

    @Override
    public List<ExamCourseChapter> getAllExamCourseChapters() {
        return examCourseChapterDao.getAllExamCourseChapters();
    }

    @Override
    public List<ExamCourseChapter> getExamCourseChapterByCourseId(Long id) {
        return examCourseChapterDao.getAllExamCourseChaptersByCourseId(id);
    }

}