package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class WriteData {
    @SerializedName("userNumber")
    private String userNumber;

    @SerializedName("writingTime")
    private String writingTime;

    @SerializedName("recruitMember")
    private String recruitMember;

    @SerializedName("recruitPeriod")
    private String recruitPeriod;

    @SerializedName("part")
    private String part;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    public WriteData(String userNumber, String writingTime, String recruitMember, String recruitPeriod, String part, String title, String content) {
        this.userNumber = userNumber;
        this.writingTime = writingTime;
        this.recruitMember = recruitMember;
        this.recruitPeriod = recruitPeriod;
        this.part = part;
        this.title = title;
        this.content = content;
    }
}
