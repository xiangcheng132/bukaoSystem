package com.bukaoSystem.model;

public class ExamAnswerSheetDetail {
    private Long id;
    private Long answerId;
    private Long resourceId;
    private String userKey;
    private String isTrue;
    private String createTime;
    private ExamResources examResources; // 新增的字段

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Boolean isTrue) {
        this.isTrue = String.valueOf(isTrue);
    }
    public void setIsTrue(String isTrue) {
        this.isTrue = isTrue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ExamResources getExamResources() {
        return examResources;
    }

    public void setExamResources(ExamResources examResources) {
        this.examResources = examResources;
    }
}
