package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stuty.studymatching.FRAGMENT.BoardPage_Main;
import com.stuty.studymatching.FRAGMENT.FirstCreatePage;
import com.stuty.studymatching.FRAGMENT.MainPage;
import com.stuty.studymatching.FRAGMENT.SecondCreatePage;
import com.stuty.studymatching.OBJECT.User;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UserData;
import com.stuty.studymatching.SERVER.UserInDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainTabActivity extends AppCompatActivity implements MainPage.LogoutListener, FirstCreatePage.FirstPageListener,
        BoardPage_Main.BoardPageListener,UserInDatabase.GetUserListener {

    User currentUser = new User();

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

    private UserInDatabase userInDatabase;
    private FirebaseAuth mAuth;
    private int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_search_24,
            R.drawable.baseline_create_24
    };
    private ServiceApi service;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        mContext = this;

        userInDatabase = new UserInDatabase(service);
        userInDatabase.setListener((UserInDatabase.GetUserListener) mContext);
//        getHashKey();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingView);
        slidingUpPanelLayout.setTouchEnabled(false);

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userInDatabase.getUser(new UserData(user.getUid()));
        Log.d("sequence","getUser");

        //메인탭액티비티 최초 진입 시 메인화면 호출
        callFragment(FRAGMENT1);

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
                BoardPage_Main boardPage_main = new BoardPage_Main().newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("address",currentUser.getAddress());
                boardPage_main.setArguments(bundle);
                transaction.replace(R.id.main_container, boardPage_main);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
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
//        callFragment(FRAGMENT3);
    }

    @Override
    public void writeBtClick() {
        callFragment(FRAGMENT3);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        Log.d("sequence","getInfo");
        Gson gson = new Gson();
        currentUser = gson.fromJson(jsonArray.get(0).toString(),User.class);
    }


    //KeyHash 얻는 코드
   /* private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }*/


}