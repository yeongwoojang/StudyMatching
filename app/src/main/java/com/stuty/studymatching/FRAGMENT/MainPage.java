package com.stuty.studymatching.FRAGMENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.facebook.internal.metrics.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.stuty.studymatching.R;


public class MainPage extends Fragment {

    private Context mContext = null;
    private LogoutListener logoutListener;


    private DrawerLayout mDrawerLayout;
    private ImageButton menuBt;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private CardView cardView;
    private ImageView profileIV;
    private EditText editText1,editText2,editText3,editText4;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MainPage newInstance(){return new MainPage();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logoutListener = (LogoutListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainpage, container, false);
        mDrawerLayout = rootView.findViewById(R.id.drawer_layout);
        menuBt = rootView.findViewById(R.id.menu_button);
        navigationView = rootView.findViewById(R.id.nav_view);
        editText1 = rootView.findViewById(R.id.nickNameET);
        editText2 = rootView.findViewById(R.id.idET);
        editText3 = rootView.findViewById(R.id.genderET);
        editText4 = rootView.findViewById(R.id.majorET);
        profileIV = rootView.findViewById(R.id.myface);
        cardView = rootView.findViewById(R.id.cardview);

        if (user != null) {
            for(UserInfo profile : user.getProviderData()){
                //로그인 제공 사이트
                String providerId = profile.getProviderId();
                //로그인 한 유저의 이름, 이메일, 프사
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();

                //유저의 이메일 검증 체크
                boolean emailVerified = user.isEmailVerified();

                String uid = user.getUid();
            }
        }

        editText1.setText(user.getDisplayName());
        editText2.setText(user.getEmail());
        profileIV.setImageURI(user.getPhotoUrl());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        menuBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id == R.id.setting) {

                } else if (id == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    logoutListener.successLogout();
                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public interface LogoutListener{
        void successLogout();
    }


    //프로필 수정
    public void updateUserProfile(){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("user")
                .setPhotoUri(Uri.parse(""))
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("MainPage","User profile updated.");
                        }
                    }
                });
    }
}
