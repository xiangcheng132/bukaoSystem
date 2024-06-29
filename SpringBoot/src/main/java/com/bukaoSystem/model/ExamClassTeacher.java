package com.bukaoSystem.model;

public class ExamClassTeacher {
    private Long id;
    private Long classId;
    private Long teacherId;
    private String createTime;

    public ExamClassTeacher() {
    }
    public ExamClassTeacher(Long id, Long classId, Long teacherId, String createTime) {
        this.id = id;
        this.classId = classId;
        this.teacherId = teacherId;
        this.createTime = createTime;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
