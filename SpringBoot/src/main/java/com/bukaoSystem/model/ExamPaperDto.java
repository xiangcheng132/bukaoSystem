package com.bukaoSystem.model;

public class ExamPaperDto {
    private String name;
    private String comment;
    private String place;
    private Long courseId;
    private Long classId;
    private String beginTime;
    private String endTime;
    private int singleChoiceCount;
    private int trueFalseCount;
    private int fillInBlankCount;
    private int bigquestionCount;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
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

    public int getSingleChoiceCount() {
        return singleChoiceCount;
    }

    public void setSingleChoiceCount(int singleChoiceCount) {
        this.singleChoiceCount = singleChoiceCount;
    }

    public int getTrueFalseCount() {
        return trueFalseCount;
    }

    public void setTrueFalseCount(int trueFalseCount) {
        this.trueFalseCount = trueFalseCount;
    }

    public int getFillInBlankCount() {
        return fillInBlankCount;
    }

    public void setFillInBlankCount(int fillInBlankCount) {
        this.fillInBlankCount = fillInBlankCount;
    }

    public int getBigquestionCount() {
        return bigquestionCount;
    }

    public void setBigquestionCount(int bigquestionCount) {
        this.bigquestionCount = bigquestionCount;
    }
}

