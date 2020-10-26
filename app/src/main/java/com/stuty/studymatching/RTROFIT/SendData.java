package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;
import com.stuty.studymatching.OBJECT.Writing;

public class SendData {

    @SerializedName("recievedUser")
    private String recievedUser;

    @SerializedName("recievedUserNumber")
    private int recievedUserNumber;

    @SerializedName("writingNumber")
    private int writingNumber;

    @SerializedName("senderNumber")
    private int senderNumber;

    @SerializedName("sender")
    private String sender;

    public SendData(Writing recievedUserInfo,int senderNumber,String sender) {
        this.recievedUser = recievedUserInfo.getUserName();
        this.recievedUserNumber = recievedUserInfo.getUserNumber();
        this.writingNumber = recievedUserInfo.getWritingNumber();
        this.senderNumber = senderNumber;
        this.sender = sender;
    }
}
