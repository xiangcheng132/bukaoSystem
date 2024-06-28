package com.bukaoSystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExamClass {
    private Long id;
    private String name;
    private String comment;
    private LocalDateTime createTime;

    public ExamClass() {
    }

    public ExamClass(Long id, String name, String comment, String createTime) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        LocalDateTime localDateTime = LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.createTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.createTime = localDateTime;
    }
}