package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class JwtResponse {


    @SerializedName("firebase_token")
    private String firebase_token;


    public String getFirebase_token() {
        return firebase_token;
    }
}
