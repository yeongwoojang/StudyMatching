package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class FirebaseJwt  {
    @SerializedName("token")
    String token;

    public FirebaseJwt(String token) {
        this.token = token;
    }
}
