package com.stuty.studymatching.KAKAO;

import android.util.Log;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.stuty.studymatching.SERVER.DatabaseCheck;
import com.stuty.studymatching.RTROFIT.CheckData;

import java.io.IOException;

public class SessionCallback implements ISessionCallback {
    DatabaseCheck dbCheck;
    KakaoLoginListener listener;

    public SessionCallback(DatabaseCheck dbCheck) {
        this.dbCheck = dbCheck;
    }

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        try {
            requestMe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    public void requestMe() throws IOException {
        AuthService.getInstance()
                .requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "토큰 정보 요청 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(AccessTokenInfoResponse result) {
                        Log.i("KAKAO_API", "사용자 아이디: " + result.getUserId());
                        Log.i("KAKAO_API", "남은 시간(s): " + result.getExpiresInMillis());
                        ;
                        Log.i("KAKAO_API", "result" + result);
                    }
                });

        UserManagement.getInstance()
                .me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);


                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                        UserAccount kakaoAccount = result.getKakaoAccount();
                        if (kakaoAccount != null) {
//                                redirectSignupActivity();
                            // 이메일
                            String email = kakaoAccount.getEmail();
                            Log.i("KAKAO_API", "kakaoacount: " + kakaoAccount.getPhoneNumber());
                            Log.i("KAKAO_API", "kakaoacount: " + kakaoAccount);
                            if (email != null) {
                                Log.i("KAKAO_API", "email: " + email);

                            } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 이메일 획득 가능
                                // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                            } else {
                                // 이메일 획득 불가
                            }

                            // 프로필
                            Profile profile = kakaoAccount.getProfile();
                            Log.d("KAKAO_API", profile + "");
                            if (profile != null) {
                                Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());
                                dbCheck.startCheck(new CheckData("kakao",kakaoAccount.getEmail()),"kakao",kakaoAccount.getEmail(),profile.getNickname(),null);
                                listener.success();
                            } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 프로필 정보 획득 가능

                            } else {
                                // 프로필 획득 불가
                            }
                            //추가
                        } else {
                            Log.d("KAKAO_API", kakaoAccount + "");
                        }
                    }
                });
    }
    public interface KakaoLoginListener {
        void success();
    }
    public void setListner(KakaoLoginListener listener) {
        this.listener = listener;
    }
}

