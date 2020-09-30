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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.stuty.studymatching.SERVER.DatabaseCheck;
import com.stuty.studymatching.KAKAO.SessionCallback;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.CheckData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements SessionCallback.KakaoLoginListener {

    public static final int RC_SIGN_IN = 10;

    private CallbackManager mCallbackManager;

    private Context mContext = null;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private ServiceApi service;
    private Button google_sign_in_button,facebook_sign_in_button,kakao_sign_in_button;

    private DatabaseCheck dbCheck;
    private SessionCallback sessionCallback;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        service = RetrofitClient.getClient().create(ServiceApi.class);
        dbCheck = new DatabaseCheck(service);

        sessionCallback = new SessionCallback(dbCheck);


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
        //페이스북로그인 버튼클릭 이벤트
        mCallbackManager = CallbackManager.Factory.create();
        facebook_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin();
            }
        });

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
        Log.d("execute","onActivityResult");
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
//                dbCheck.startCheck(new CheckData(account.getEmail()),account.getEmail(),account.getDisplayName());
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("execute","firebaseAuthWithGoogle");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            dbCheck.startCheck(new CheckData("google",user.getEmail()),"google",user.getEmail(),user.getDisplayName(),null);
                            Intent toMainPageIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(toMainPageIntent);

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void facebookLogin(){
        Log.d("execute","facebookLogin");
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
        Log.d("execute","firebaseAuthWithFacebook");
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("facebook",user.getPhoneNumber());
                            dbCheck.startCheck(new CheckData("facebook",user.getEmail()),"facebook",user.getEmail(),user.getDisplayName(),null);
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

}