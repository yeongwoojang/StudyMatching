package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;
import com.stuty.studymatching.OBJECT.Writing;

public class SendData {

    @SerializedName("recievedUserNumber")
    private int recievedUserNumber;

    @SerializedName("recievedUser")
    private String recievedUser;

    @SerializedName("writingNumber")
    private int writingNumber;

    @SerializedName("writingTitle")
    private String writingtitle;

    @SerializedName("senderNumber")
    private int senderNumber;

    @SerializedName("sender")
    private String sender;

    @SerializedName("requestTime")
    private String requestTime;

    public SendData(Writing recievedUserInfo,int senderNumber,String sender,String requestTime) {
        this.recievedUserNumber = recievedUserInfo.getUserNumber();
        this.recievedUser = recievedUserInfo.getUserName();
        this.writingNumber = recievedUserInfo.getWritingNumber();
        this.writingtitle = recievedUserInfo.getTitle();
        this.senderNumber = senderNumber;
        this.sender = sender;
        this.requestTime = requestTime;
    }
}
