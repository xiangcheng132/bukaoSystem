package com.bukaoSystem.model;

public class GradingResult {
    private Long answerId;
    private Long detailId;
    private double score;
    private boolean isTrue;

    // Getters and setters

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }
}

