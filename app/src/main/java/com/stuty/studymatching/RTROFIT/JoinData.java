package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class JoinData {

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userName")
    private String userName;


    public JoinData(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }
}