package com.twelve.challengeapp.dto;

public class CommentDto {
    private String content;

    // 기본 생성자
    public CommentDto() {}

    // 매개변수가 있는 생성자
    public CommentDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
