package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.stuty.studymatching.ACTIVITY.RTROFIT.CheckData;
import com.stuty.studymatching.ACTIVITY.RTROFIT.CheckResponse;
import com.stuty.studymatching.ACTIVITY.RTROFIT.JoinData;
import com.stuty.studymatching.ACTIVITY.RTROFIT.JoinResponse;
import com.stuty.studymatching.ACTIVITY.RTROFIT.RetrofitClient;
import com.stuty.studymatching.ACTIVITY.RTROFIT.ServiceApi;
import com.stuty.studymatching.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 10;

    private Context mContext = null;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private ServiceApi service;

    private SignInButton googleLoginBt;

    private Boolean isStoredUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();

        googleLoginBt = (SignInButton) findViewById(R.id.google_login_button);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        if (mAuth.getCurrentUser() != null) {
            Intent toMainPageIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(toMainPageIntent);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        googleLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken(),account.getDisplayName());
                startCheck(new CheckData(account.getIdToken()),account.getIdToken(),account.getDisplayName());

            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken,String userName) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent toMainPageIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(toMainPageIntent);

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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


    private void startCheck(CheckData data, final String idToken, final String userName) {
        service.userCheck(data).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                CheckResponse result = response.body();
                Log.d("result",result.getMessage()+"");
                if(result.getMessage() ==true){
                    startJoin(new JoinData(idToken,userName));
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.e("체크 에러 발생", t.getMessage());
            }
        });
    }
}