package com.stuty.studymatching.RTROFIT;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


//Retrofit2를 이용하여 서버에 요청할 메소드를 선언해놓은 모아놓은 인터페이스
public interface ServiceApi {

//-----------회원가입 및 로그인 시 사용------------------
    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/check")
    Call<CheckResponse> userCheck(@Body CheckData data);
//--------------------------------------------------------

//    카카오계정 파이어베이스에 연동할 때 사용
    @POST("/verifyToken")
    Call<JwtResponse> getFirebaseJwt(@Body FirebaseJwt data);

//    게시글 DB저장을 서버에 요청하는 것
    @POST("/write")
    Call<WriteResponse> writeToDB(@Body WriteData data);

//    선택한 주소에 맞는 게시글의 내용을 서버를 통해 DB에서 불러오는 것
    @POST("/board/info")
    Call<AddressResponse> getAddressBoard(@Body AddressData data);

//    작성한 댓글을 DB에 저장할 것을 서버에 요청하는 것
    @POST("/write/comment")
    Call<WriteCommentResponse> writeComment(@Body CommentData data);

//    현재 유저의 uid를 서버에 넘겨 그에 맞는 유저 정보를 요청하는 것
    @POST("/user/info")
    Call<UserResponse> getUserInfo (@Body UidData data);

    //글번호를 서버에 넘겨 그에 맞는 댓글목록을 요청하는 것
    @POST("/read/comment")
    Call<ReadCommentResponse> getComments (@Body WritingNumberData data);

    //DB에 저장된 유저의 모든 지역이름을 요청하는 것
    @POST("/board/area")
    Call<AreaResponse> getAreaData();

    //게시글의 전체 목록을 서버에 요청하는 것
    @POST("/board/entire")
    Call<EntireBoardResponse> getEntireWriting();

//    유저의 디바이스 토큰정보 갱신또는 저장을 서버에 요청하는 것
    @POST("/fcm/updateToken")
    Call<UpdateTokenResponse> updateDeviceToken (@Body UpdateTokenData data);

//    선택한 유저에세 fcm메시지 전송을 서버에 요청하는 것
    @POST("/fcm")
    Call<FcmResponse> sendFcm(@Body FcmData data);

    @POST("/fcm/recievedData")
    Call<ReceivedDataResponse> getRecievedData (@Body RecievedUserData data);

    @POST("/fcm/sendData")
    Call<SendResponse> sendData(@Body SendData data);

    @POST("/fcm/updateCheck")
    Call<UpdateCheckReqResponse> updateCheckReq (@Body RecievedNumberData data);
}
