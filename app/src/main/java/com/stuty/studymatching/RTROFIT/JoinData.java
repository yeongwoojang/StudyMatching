package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("signInMethod")
    private String signInMethod;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userPassword")
    private String userPassword;

    public JoinData(String signInMethod, String userEmail, String userName,String userPassword) {
        this.signInMethod = signInMethod;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}