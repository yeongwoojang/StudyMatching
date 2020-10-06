package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stuty.studymatching.FRAGMENT.FirstCreatePage;
import com.stuty.studymatching.FRAGMENT.MainPage;
import com.stuty.studymatching.FRAGMENT.SecondCreatePage;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;

public class MainTabActivity extends AppCompatActivity implements MainPage.LogoutListener, FirstCreatePage.FirstPageListener
        , SecondCreatePage.SecondPageListener {


    private TabLayout tabLayout;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private Button postingBt,text;
    private ImageButton closeBt;
    FirstCreatePage firstCreatePage;
    SecondCreatePage secondCreatePage;
    private long backKeyPressed = 0;
    private Toast backBtClickToast;

    private final int FRAGMENT1 = 0;
    private final int FRAGMENT2 = 1;
    private final int FRAGMENT3 = 2;
    private final int FRAGMENT4 = 3;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_search_24,
            R.drawable.baseline_create_24
    };
    private ServiceApi service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingView);
        postingBt = (Button) findViewById(R.id.posting_bt);
        closeBt = (ImageButton) findViewById(R.id.close_bt);
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.getTabAt(FRAGMENT1).setTag(FRAGMENT1);
        tabLayout.getTabAt(FRAGMENT1).setIcon(tabIcons[0]);

        tabLayout.getTabAt(FRAGMENT2).setTag(FRAGMENT2);
        tabLayout.getTabAt(FRAGMENT2).setIcon(tabIcons[1]);

        tabLayout.getTabAt(FRAGMENT3).setTag(FRAGMENT3);
        tabLayout.getTabAt(FRAGMENT3).setIcon(tabIcons[2]);

        //메인탭액티비티 최초 진입 시 메인화면 호출
        callFragment(FRAGMENT1);
        //FirstCreatePage미리 생성
//        callFragment(FRAGMENT3);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (Integer.parseInt(String.valueOf(tab.getTag()))) {
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
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (Integer.parseInt(String.valueOf(tab.getTag()))) {
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
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        break;
                }

            }
        });
        slidingUpPanelLayout.setPanelHeight(0);

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if ((slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                        || slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                } else {

                }

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

    private void callFragment(int fragmentId) {
        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentId) {
            default:
            case FRAGMENT1:
                // '프래그먼트1' 호출
                MainPage mainPage = new MainPage().newInstance();
                transaction.replace(R.id.main_container, mainPage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case FRAGMENT2:
                // '프래그먼트2' 호출
                break;
            case FRAGMENT3:
                // '프래그먼트3' 호출
                firstCreatePage = new FirstCreatePage().newInstance();
                transaction.replace(R.id.create_page_container, firstCreatePage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();

                break;
            case FRAGMENT4:
                // '프래그먼트4' 호출
                secondCreatePage = new SecondCreatePage().newInstance();
                transaction.add(R.id.create_page_container, secondCreatePage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void successLogout() {
        finish();
    }

    @Override
    public void nextBtClick() {
        callFragment(FRAGMENT4);

    }

    @Override
    public void closeBtClick() {
        slidingUpPanelLayout.setPanelHeight(0);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FirstCreatePage firstCreatePage = new FirstCreatePage().newInstance();
        transaction.remove(firstCreatePage);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void postingBtClick() {
        //Drawer를 닫으면서 CreatePage를 초기화면으로 돌려놓는다.
        slidingUpPanelLayout.setPanelHeight(0);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        callFragment(FRAGMENT3);
    }

    @Override
    public void prevBtClick() {
//        callFragment(FRAGMENT3);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().popBackStack();
//        FirstCreatePage firstCreatePage = new FirstCreatePage().newInstance();
//        transaction.replace(R.id.create_page_container,firstCreatePage);
//        transaction.commitAllowingStateLoss();
    }


}