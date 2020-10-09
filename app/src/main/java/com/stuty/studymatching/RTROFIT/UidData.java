package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class UidData {
    @SerializedName("uid")
    private String uid;

    public UidData(String uid) {
        this.uid = uid;
    }
}
