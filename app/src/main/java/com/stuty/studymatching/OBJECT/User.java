package com.stuty.studymatching.OBJECT;

public class User {
    private String userNumber;
    private String signInmethod;
    private String uid;
    private String userName;
    private String address;


    public String getUserNumber() {
        return userNumber;
    }


    public String getSignInmethod() {
        return signInmethod;
    }

    public String getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setSignInmethod(String signInmethod) {
        this.signInmethod = signInmethod;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
