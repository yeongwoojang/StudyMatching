package com.study.studymatching.FACEBOOK;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginCallback implements FacebookCallback<LoginResult> {



    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("CallBack :: ","onSuccess");
        requestMe(loginResult.getAccessToken());
    }


    @Override
    public void onCancel() {
        Log.e("CallBack :: ","onCance;");
    }

    /**
     * Called when the dialog finishes with an error.
     *
     * @param error The error that occurred
     */
    @Override
    public void onError(FacebookException error) {
        Log.e("Callback :: ","onError : " + error.getMessage());
    }

    public void requestMe(AccessToken token){
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("result",object.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
}
