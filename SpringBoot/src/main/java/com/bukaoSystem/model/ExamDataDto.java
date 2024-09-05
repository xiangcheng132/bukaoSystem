package com.bukaoSystem.model;

public class ExamDataDto {
    private Long examPaperCount;
    private Long questionCount;
    private Long doExamPaperCount;
    private Long doQuestionCount;

    public Long getExamPaperCount() {
        return examPaperCount;
    }

    public void setExamPaperCount(Long examPaperCount) {
        this.examPaperCount = examPaperCount;
    }

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

    public Long getDoExamPaperCount() {
        return doExamPaperCount;
    }

    public void setDoExamPaperCount(Long doExamPaperCount) {
        this.doExamPaperCount = doExamPaperCount;
    }

    public Long getDoQuestionCount() {
        return doQuestionCount;
    }

    public void setDoQuestionCount(Long doQuestionCount) {
        this.doQuestionCount = doQuestionCount;
    }
}
