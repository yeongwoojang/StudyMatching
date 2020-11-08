package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class CommentData {

    @SerializedName("writingNumber")
    private int writingNumber;

    @SerializedName("userNumber")
    private int userNumber;

    @SerializedName("userName")
    private String userName;

    @SerializedName("commentContent")
    private String commentContent;

    @SerializedName("commentTime")
    private String commentTime;

    public CommentData(int writingNumber, int userNumber, String userName, String commentContent, String commentTime) {
        this.writingNumber = writingNumber;
        this.userNumber = userNumber;
        this.userName = userName;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }
}
