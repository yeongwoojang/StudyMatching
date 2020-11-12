package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stuty.studymatching.ACTIVITY.SearchAddress;
import com.stuty.studymatching.OBJECT.User;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.AddressData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.SERVER.RequestForLogin;
import com.stuty.studymatching.SERVER.RequestForUser;

public class FirstSettingActivity extends AppCompatActivity {

    private ImageButton set_image;
    private TextView email_tv;
    private EditText set_nickname;
    private Spinner set_gender;
    private Button fix_setting,set_myArea;
    private FirebaseAuth mAuth;

    private String gender;
    private static final  int SEARCH_ADDRESS_ACTIVITY = 10000;

    private RequestForLogin requestForLogin;

    ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        set_image = (ImageButton)findViewById(R.id.set_image);
        email_tv = (TextView)findViewById(R.id.email_tv);
        set_nickname = (EditText)findViewById(R.id.set_nickname);
        set_myArea = (Button)findViewById(R.id.set_myArea);
        set_gender = (Spinner)findViewById(R.id.set_gender);
        fix_setting = (Button)findViewById(R.id.fix_setting);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        requestForLogin = new RequestForLogin(service);

        set_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = set_gender.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"성별 선택",Toast.LENGTH_SHORT).show();
            }
        });
        email_tv.setText(user.getEmail());
        set_myArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchAddress.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
        fix_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForLogin.updateAddress(set_myArea.getText().toString(),set_nickname.getText().toString(),gender,user.getUid());
                Intent intent = new Intent(getApplicationContext(), MainTabActivity.class);
                startActivity(intent);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.d("주소 데이터",data);
                        set_myArea.setText(data);
                    }
                }
                break;
        }
    }
}