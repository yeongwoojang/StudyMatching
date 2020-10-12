package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("uid")
    private String uid;


    public UserData(String uid) {
        this.uid = uid;
    }
}
