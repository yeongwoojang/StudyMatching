package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class UserNumberResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userNumber")
    private String userNumber;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getUserNumber() {
        return userNumber;
    }

}
