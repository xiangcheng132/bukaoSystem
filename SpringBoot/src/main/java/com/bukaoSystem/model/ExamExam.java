package com.bukaoSystem.model;

import java.util.List;

public class ExamExam {
    private Long id;
    private Long courseId;
    private String name;
    private String comment;
    private String place;
    private int state;
    private String beginTime;
    private String endTime;
    private String createTime;

    private List<ExamClass> examClasses;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ExamClass> getExamClasses() {
        return examClasses;
    }

    public void setExamClasses(List<ExamClass> examClasses) {
        this.examClasses = examClasses;
    }
}
