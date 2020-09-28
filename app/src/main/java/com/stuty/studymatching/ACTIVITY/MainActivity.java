package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.stuty.studymatching.FRAGMENT.TabMenu1;
import com.stuty.studymatching.FRAGMENT.TabMenu2;
import com.stuty.studymatching.FRAGMENT.TabMenu3;
import com.stuty.studymatching.R;



public class MainActivity extends AppCompatActivity {

    private Context mContext =null;

    private long backKeyPressed = 0;
    private Toast backBtClickToast;
    private DrawerLayout mDrawerLayout;
    private ImageButton menuBt;

    //TabLayout
    TabLayout tabs;
    TabMenu1 tabMenu1;
    TabMenu2 tabMenu2;
    TabMenu3 tabMenu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuBt = (ImageButton)findViewById(R.id.menu_button);

        menuBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

// ServiceApi 객체 생성


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id==R.id.setting){

                }else if(id==R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    finish();

                }

                return true;
            }
        });

        //tablayout
        tabMenu1 = new TabMenu1();
        tabMenu2 = new TabMenu2();
        tabMenu3 = new TabMenu3();

        getSupportFragmentManager().beginTransaction().add(R.id.container, tabMenu1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("주변 스터디"));
        tabs.addTab(tabs.newTab().setText("채팅"));
        tabs.addTab(tabs.newTab().setText("마이 페이지"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position==0)
                    selected = tabMenu1;
                else if(position == 1)
                    selected = tabMenu2;
                else if(position == 2)
                    selected = tabMenu3;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressed + 2000) {
                backKeyPressed = System.currentTimeMillis();
                backBtClickToast = Toast.makeText(this, "\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                backBtClickToast.show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressed + 2000) {
                finishAffinity();
                backBtClickToast.cancel();
            }
         else {
            super.onBackPressed();
        }
    }

}