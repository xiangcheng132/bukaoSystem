package com.bukaoSystem.model;

public class ExamClassDto {
    private Long id;
    private String name;
    private String comment;
    private String teachername;
    private String createTime;

    public ExamClassDto(Long id, String name, String comment, String teachername, String createTime) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.teachername = teachername;
        this.createTime = createTime;
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

    public String getTeacher() {
        return teachername;
    }

    public void setTeacher(String teacher) {
        this.teachername = teacher;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
