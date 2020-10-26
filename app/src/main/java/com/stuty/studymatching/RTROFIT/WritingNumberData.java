package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class WritingNumberData {

    @SerializedName("writingNumber")
    private int writingNumber;

    public WritingNumberData(int writingNumber) {
        this.writingNumber = writingNumber;
    }
}
