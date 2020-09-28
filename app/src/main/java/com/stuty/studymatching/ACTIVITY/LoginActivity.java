package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.stuty.studymatching.SERVER.DatabaseCheck;
import com.stuty.studymatching.KAKAO.SessionCallback;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.CheckData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;

<<<<<<<<< Temporary merge branch 1
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
=========
import java.util.Arrays;
>>>>>>>>> Temporary merge branch 2

public class LoginActivity extends AppCompatActivity implements SessionCallback.KakaoLoginListener {

    public static final int RC_SIGN_IN = 10;

    /*facebook*/
    private Button facebook_sign_in_button;
    private CallbackManager mCallbackManager;

    /*google*/
    private Context mContext = null;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button google_sign_in_button;
    private ServiceApi service;
    private Button google_sign_in_button,kakao_sign_in_button;

    private DatabaseCheck dbcheck;
    private SessionCallback sessionCallback;
    Session session;
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
        service = RetrofitClient.getClient().create(ServiceApi.class);
        dbcheck = new DatabaseCheck(service);

        sessionCallback = new SessionCallback(dbcheck);


        google_sign_in_button = (Button) findViewById(R.id.google_sign_in_button);
        facebook_sign_in_button = (Button)findViewById(R.id.facebook_sign_in_button);
        kakao_sign_in_button = (Button)findViewById(R.id.kakao_sign_in_button);

        if (mAuth.getCurrentUser() != null) {
            Intent toMainPageIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(toMainPageIntent);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //구글로그인 버튼클릭 이벤트
        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        /*페이스북*/
        facebook_sign_in_button = (Button)findViewById(R.id.facebook_sign_in_button);
        mCallbackManager = CallbackManager.Factory.create();
        facebook_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin();
            }
        });
        mCallbackManager = CallbackManager.Factory.create();

        //카카오톡로그인 버튼클릭 이벤트
        kakao_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionCallback.setListner((SessionCallback.KakaoLoginListener) mContext);
                session = Session.getCurrentSession();
                session.addCallback(sessionCallback);
                session.open(AuthType.KAKAO_TALK, LoginActivity.this);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                dbcheck.startCheck(new CheckData(account.getEmail()),account.getEmail(),account.getDisplayName());
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
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
    private void facebookLogin(){
        Log.d("facebook","facebookLogin()");
        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("public_profile","email"));

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseAuthWithFacebook(loginResult.getAccessToken());
                Toast.makeText(getApplicationContext(),"페북 로그인 성공",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void firebaseAuthWithFacebook(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("LoginSuccess",user.getDisplayName()+"");
                            Intent toMainPageIntent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(toMainPageIntent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"페북 로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void success() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
//    private void startJoin(JoinData data) {
//        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
//            @Override
//            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
//                JoinResponse result = response.body();
//                if (result.getCode() == 200) {
//                    Log.d("resultCode",result.getCode()+"");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JoinResponse> call, Throwable t) {
//                Log.e("회원가입 에러 발생", t.getMessage());
//            }
//        });
//    }
//
//
//    private void startCheck(CheckData data, final String idToken, final String userName) {
//        service.userCheck(data).enqueue(new Callback<CheckResponse>() {
//            @Override
//            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
//                CheckResponse result = response.body();
//                Log.d("result",result.getMessage()+"");
//                if(result.getMessage() ==true){
//                    startJoin(new JoinData(idToken,userName));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CheckResponse> call, Throwable t) {
//                Log.e("체크 에러 발생", t.getMessage());
//            }
//        });
//    }
}