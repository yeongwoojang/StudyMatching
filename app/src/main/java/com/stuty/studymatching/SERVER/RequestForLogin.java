package com.stuty.studymatching.SERVER;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stuty.studymatching.RTROFIT.AddressData;
import com.stuty.studymatching.RTROFIT.CheckData;
import com.stuty.studymatching.RTROFIT.CheckResponse;
import com.stuty.studymatching.RTROFIT.FirebaseJwt;
import com.stuty.studymatching.RTROFIT.JoinData;
import com.stuty.studymatching.RTROFIT.JoinResponse;
import com.stuty.studymatching.RTROFIT.JwtResponse;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UpdateTokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForLogin {

    private ServiceApi service;

    public RequestForLogin(ServiceApi service) {
        this.service = service;
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                if (result.getCode() == 200) {
                    Log.d("resultCode",result.getCode()+"");
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    public void updateAddress(String address,String nickname,String gender,String uid){
        service.updateAddress(address,nickname,gender,uid).enqueue(new Callback<UpdateTokenResponse>() {
            @Override
            public void onResponse(Call<UpdateTokenResponse> call, Response<UpdateTokenResponse> response) {
            UpdateTokenResponse result = response.body();
            Log.d("result",result.getCode()+"");
            }

            @Override
            public void onFailure(Call<UpdateTokenResponse> call, Throwable t) {
                Log.e("주소 수정 에러", t.getMessage());
            }
        });
    }

     public void startCheck(CheckData data,final String signInMethod, final String userUid, final String userName) {
        service.userCheck(data).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                CheckResponse result = response.body();

                Log.d("result",result.getMessage()+"");
                if(result.getMessage() ==true){
                    startJoin(new JoinData(signInMethod,userUid,userName));
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.e("체크 에러 발생", t.getMessage());
            }
        });
}


    public Task<String> getFirebaseJwt(FirebaseJwt data) {
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();

        service.getFirebaseJwt(data).enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                JwtResponse result = response.body();
                if(result.getFirebase_token()!=null){
                    Log.d("firebaseToken",result.getFirebase_token());
                    source.setResult(result.getFirebase_token());
//                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                   String firebaseToken = result.getFirebase_token();
//                    mAuth.signInWithCustomToken(firebaseToken);
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    startCheck(new CheckData("kakao",user.getUid()),"kakao",user.getUid(),user.getDisplayName());
                }else{
                    Log.d("ERROR","get Custom Token Error");
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {

            }
        });
        return source.getTask();

    }
}
