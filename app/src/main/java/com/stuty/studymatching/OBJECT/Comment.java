package com.stuty.studymatching.OBJECT;

public class Comment {
    private int userNumber;
    private String username;
    private String commentContent;
    private String commentTime;

    public Comment(int userNumber, String username, String commentContent, String commentTime) {
        this.userNumber = userNumber;
        this.username = username;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
