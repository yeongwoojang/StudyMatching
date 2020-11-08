package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("object")
    private String object;

    @SerializedName("userNumber")
    private String userNumber;

    @SerializedName("signInmethod")
    private String signInmethod;

    @SerializedName("userName")
    private String userName;

    @SerializedName("address")
    private String address;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getObject() {
        return object;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getSignInmethod() {
        return signInmethod;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }
}
