package com.stuty.studymatching.RTROFIT;

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

}
