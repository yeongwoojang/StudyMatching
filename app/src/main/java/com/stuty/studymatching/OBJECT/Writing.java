package com.stuty.studymatching.OBJECT;

import java.io.Serializable;

public class Writing implements Serializable {
    private String userName;
    private int userNumber;
    private int writingNumber;
    private String writingTime;
    private String recruitMember;
    private String recruitPeriod;
    private String part;
    private String title;
    private String content;


    public Writing(String userName, int userNumber, int writingNumber, String writingTime, String recruitMember, String recruitPeriod, String part, String title, String content) {
        this.userName = userName;
        this.userNumber = userNumber;
        this.writingNumber = writingNumber;
        this.writingTime = writingTime;
        this.recruitMember = recruitMember;
        this.recruitPeriod = recruitPeriod;
        this.part = part;
        this.title = title;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public int getWritingNumber() {
        return writingNumber;
    }

    public String getWritingTime() {
        return writingTime;
    }

    public String getRecruitMember() {
        return recruitMember;
    }

    public String getRecruitPeriod() {
        return recruitPeriod;
    }

    public String getPart() {
        return part;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public void setWritingNumber(int writingNumber) {
        this.writingNumber = writingNumber;
    }

    public void setWritingTime(String writingTime) {
        this.writingTime = writingTime;
    }

    public void setRecruitMember(String recruitMember) {
        this.recruitMember = recruitMember;
    }

    public void setRecruitPeriod(String recruitPeriod) {
        this.recruitPeriod = recruitPeriod;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
