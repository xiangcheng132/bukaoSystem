package com.bukaoSystem.model;

import java.time.LocalDateTime;

public class ExamTeacherCourse {
    private long id;



    private Long teacherId;
    private Long courseId;
    private String createTime;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
