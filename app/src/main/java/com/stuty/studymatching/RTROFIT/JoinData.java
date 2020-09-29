package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("signInMethod")
    private String signInMethod;

    @SerializedName("uid")
    private String uid;

    @SerializedName("userName")
    private String userName;


    public JoinData(String signInMethod, String uid, String userName) {
        this.signInMethod = signInMethod;
        this.uid = uid;
        this.userName = userName;
    }
}