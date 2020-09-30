package com.stuty.studymatching.ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.stuty.studymatching.FRAGMENT.CreatePage;
import com.stuty.studymatching.R;

public class MainTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private long backKeyPressed = 0;
    private Toast backBtClickToast;

    private final int FRAGMENT1 = 0;
    private final int FRAGMENT2 = 1;
    private final int FRAGMENT3 = 2;


    private int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_search_24,
            R.drawable.baseline_create_24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        tabLayout = (TabLayout)findViewById(R.id.tabs);

        for(int i=0;i<tabIcons.length;i++){
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
//                transaction.commit();
        }
    }
}