package com.stuty.studymatching.OBJECT;

import java.io.Serializable;

public class ReceivedData implements Serializable {

    private int recievedNumber;
    private String recievedUser;
    private int recievedUserNumber;
    private int writingNumber;
    private int senderNumber;
    private String sender;

    public ReceivedData(int recievedNumber, String recievedUser, int recievedUserNumber, int writingNumber, int senderNumber, String sender) {
        this.recievedNumber = recievedNumber;
        this.recievedUser = recievedUser;
        this.recievedUserNumber = recievedUserNumber;
        this.writingNumber = writingNumber;
        this.senderNumber = senderNumber;
        this.sender = sender;
    }

    public int getRecievedNumber() {
        return recievedNumber;
    }

    public void setRecievedNumber(int recievedNumber) {
        this.recievedNumber = recievedNumber;
    }

    public String getRecievedUser() {
        return recievedUser;
    }

    public void setRecievedUser(String recievedUser) {
        this.recievedUser = recievedUser;
    }

    public int getRecievedUserNumber() {
        return recievedUserNumber;
    }

    public void setRecievedUserNumber(int recievedUserNumber) {
        this.recievedUserNumber = recievedUserNumber;
    }

    public int getWritingNumber() {
        return writingNumber;
    }

    public void setWritingNumber(int writingNumber) {
        this.writingNumber = writingNumber;
    }

    public int getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(int senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
