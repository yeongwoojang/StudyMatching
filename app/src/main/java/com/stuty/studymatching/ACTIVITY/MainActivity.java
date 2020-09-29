package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.auth.FirebaseAuth;
import com.stuty.studymatching.FRAGMENT.CreatePage;
import com.stuty.studymatching.R;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {

    private Context mContext = null;

    private long backKeyPressed = 0;
    private Toast backBtClickToast;

    private DrawerLayout mDrawerLayout;
    private ImageButton menuBt;
    private TabLayout tabLayout;

    private final int FRAGMENT1 = 0;
    private final int FRAGMENT2 = 1;
    private final int FRAGMENT3 = 2;
    private final String[] tabNames = {"홈", "검색", "생성"};
    private int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_search_24,
            R.drawable.baseline_create_24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout)findViewById(R.id.tabs);

        menuBt = (ImageButton) findViewById(R.id.menu_button);

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
                    finish();
                }
                return true;
            }
        });

        for(int i=0;i<tabNames.length;i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.getTabAt(FRAGMENT1).setTag(FRAGMENT1);
        tabLayout.getTabAt(FRAGMENT1).setIcon(tabIcons[0]);
        tabLayout.getTabAt(FRAGMENT2).setTag(FRAGMENT2);
        tabLayout.getTabAt(FRAGMENT2).setIcon(tabIcons[1]);
        tabLayout.getTabAt(FRAGMENT3).setTag(FRAGMENT3);
        tabLayout.getTabAt(FRAGMENT3).setIcon(tabIcons[2]);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (Integer.parseInt(String.valueOf(tab.getTag()))){
                    case FRAGMENT1:
                        // '버튼1' 클릭 시 '프래그먼트1' 호출
                        callFragment(FRAGMENT1);
                        break;

                    case FRAGMENT2:
                        // '버튼2' 클릭 시 '프래그먼트2' 호출
                        callFragment(FRAGMENT2);
                        break;
                    case FRAGMENT3:
                        callFragment(FRAGMENT3);
                }
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
        switch (item.getItemId()) {
            case android.R.id.home:
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
        } else {
            super.onBackPressed();
        }
    }

    private void callFragment(int fragment) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            default:

            case FRAGMENT1:
                // '프래그먼트1' 호출

                break;

            case FRAGMENT2:
                // '프래그먼트2' 호출

                break;
            case FRAGMENT3:
                // '프래그먼트3' 호출
                CreatePage createPage = new CreatePage().newInstance();
                transaction.replace(R.id.main_container, createPage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
        }
    }

}
