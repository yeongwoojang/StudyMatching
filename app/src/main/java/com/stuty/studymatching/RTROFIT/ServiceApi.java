package com.stuty.studymatching.RTROFIT;

import android.icu.text.IDNA;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/check")
    Call<CheckResponse> userCheck(@Body CheckData data);

    @POST("/verifyToken")
    Call<JwtResponse> getFirebaseJwt(@Body FirebaseJwt data);

    @POST("/write")
    Call<WriteResponse> writeToDB(@Body WriteData data);

    @POST("/getUserNumber")
    Call<UserNumberResponse> checkUid(@Body UidData data);

    @POST("/write/info")
    Call<InfoResponse> getWritingInfo(@Body InfoData data);

    @POST("/user/info")
    Call<UserResponse> getUserInfo (@Body UserData data);
}
