package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class JoinData {

    @SerializedName("userToken")
    private String userToken;

    @SerializedName("userName")
    private String userName;


    public JoinData(String userToken, String userName) {
        this.userToken = userToken;
        this.userName = userName;
    }
}