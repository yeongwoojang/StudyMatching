package com.stuty.studymatching.OBJECT;

import java.io.Serializable;

public class RecievedData implements Serializable {

    private int recievedNumber;
    private String recievedUser;
    private int recievedUserNumber;
    private int writingNumber;
    private String writingTitle;
    private int senderNumber;
    private String sender;
    private String requestTime;
    private int checkRequest;

    public RecievedData(int recievedNumber, String recievedUser,
                        int recievedUserNumber, int writingNumber, String writingTitle,
                        int senderNumber, String sender, String requestTime, int checkRequest) {
        this.recievedNumber = recievedNumber;
        this.recievedUser = recievedUser;
        this.recievedUserNumber = recievedUserNumber;
        this.writingNumber = writingNumber;
        this.writingTitle = writingTitle;
        this.senderNumber = senderNumber;
        this.sender = sender;
        this.requestTime = requestTime;
        this.checkRequest = checkRequest;
    }

    public int getRecievedNumber() {
        return recievedNumber;
    }

    public String getRecievedUser() {
        return recievedUser;
    }

    public int getRecievedUserNumber() {
        return recievedUserNumber;
    }

    public int getWritingNumber() {
        return writingNumber;
    }

    public String getWritingTitle() {
        return writingTitle;
    }

    public int getSenderNumber() {
        return senderNumber;
    }

    public String getSender() {
        return sender;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public int getCheckRequest() {
        return checkRequest;
    }
}
