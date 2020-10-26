package com.stuty.studymatching.OBJECT;

import java.io.Serializable;

public class User implements Serializable {
    private int userNumber;
    private String signInmethod;
    private String uid;
    private String userName;
    private String address;
    private String deviceToken;


    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getSignInmethod() {
        return signInmethod;
    }

    public void setSignInmethod(String signInmethod) {
        this.signInmethod = signInmethod;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
