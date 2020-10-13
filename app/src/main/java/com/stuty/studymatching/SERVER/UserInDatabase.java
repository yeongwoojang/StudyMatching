package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.google.gson.Gson;
import com.stuty.studymatching.OBJECT.User;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UserData;
import com.stuty.studymatching.RTROFIT.UserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInDatabase {
    private ServiceApi service;
    private GetUserListener listener;

    public UserInDatabase(ServiceApi service) {
        this.service = service;
    }

    public void getUser(UserData data) {
        service.getUserInfo(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse result = response.body();
                if (result.getCode() == 200) {
                    String json = result.getObject();
                    Log.d("json", result.getObject() + "");
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        listener.getInfo(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("디비연동 테스트", "success");
                } else {
                    Log.d("디비연동 테스트", "failed");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
    public interface GetUserListener{
        void getInfo(JSONArray jsonArray) throws JSONException;
    }
    public void setListener(UserInDatabase.GetUserListener listener){
        this.listener = listener;
    }
}
