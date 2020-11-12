package com.stuty.studymatching.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stuty.studymatching.FRAGMENT.BoardPage_Main;
import com.stuty.studymatching.FRAGMENT.WritePage;
import com.stuty.studymatching.FRAGMENT.MainPage;
import com.stuty.studymatching.OBJECT.User;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.RecievedUserData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UpdateTokenData;
import com.stuty.studymatching.RTROFIT.UidData;
import com.stuty.studymatching.SERVER.RequestForFcm;
import com.stuty.studymatching.SERVER.RequestForUser;

import org.json.JSONArray;
import org.json.JSONException;

public class MainTabActivity extends AppCompatActivity implements MainPage.LogoutListener, WritePage.WritePageListener,
        BoardPage_Main.BoardPageListener, RequestForUser.GetUserListener, RequestForFcm.RequestForFcmListener {


    User currentUser = new User();
    private TabLayout tabLayout;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private Button postingBt, text;
    private ImageButton closeBt;
    WritePage writePage;
    private long backKeyPressed = 0;
    private Toast backBtClickToast;

    private final int FRAGMENT1 = 0;
    private final int FRAGMENT2 = 1;
    private final int FRAGMENT3 = 2;
    private final int FRAGMENT4 = 3;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private RequestForUser requestForUser;
    private FirebaseAuth mAuth;
    private int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_search_24_black,
            R.drawable.ic_baseline_person_24_black
    };
    private ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    private Context mContext;
    private boolean isRecieved = false;
    private JSONArray jsonArray;
    RequestForFcm forFcm = new RequestForFcm(service);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        Log.d("fsdgsdfg", "액티비티 Oncreate");


//        service = RetrofitClient.getClient().create(ServiceApi.class);
//        RequestForFcm forFcm = new RequestForFcm(service);
//        forFcm.setListener((RequestForFcm.RequestForFcmListener) this);
        forFcm.setListener((RequestForFcm.RequestForFcmListener) this);
     //   Intent intentFromFcm = getIntent();
    //    if (intentFromFcm.getBooleanExtra("fcm", false)) {
     //       Log.d("getFCM", "getFCM");
     //       isRecieved = true;
    //        forFcm.getRecievedData(new RecievedUserData(intentFromFcm.getIntExtra("recipientNumber", 0)));
     //   } else {
     //       Log.d("getFCM", "NoGetFCM");
      //  }
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("TAG", token);
                        RequestForFcm requestForFcm = new RequestForFcm(service);
                        requestForFcm.updateDeviceToken(new UpdateTokenData(token, currentUser.getUserNumber()));
                    }
                });


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

        mContext = this;
        requestForUser = new RequestForUser(service);
        requestForUser.setListener((RequestForUser.GetUserListener) mContext);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //메인탭액티비티 최초 진입 시 메인화면 호출
//        callFragment(FRAGMENT1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (Integer.parseInt(String.valueOf(tab.getTag()))) {
                    case FRAGMENT1:
                        // '버튼1' 클릭 시 '프래그먼트1' 호출
                        callFragment(FRAGMENT1);
                        tabLayout.getTabAt(FRAGMENT1).setIcon(R.drawable.baseline_home_24);
                        tabLayout.getTabAt(FRAGMENT2).setIcon(R.drawable.baseline_search_24_black);
                        tabLayout.getTabAt(FRAGMENT3).setIcon(R.drawable.ic_baseline_person_24_black);
                        break;

                    case FRAGMENT2:
                        // '버튼2' 클릭 시 '프래그먼트2' 호출
                        callFragment(FRAGMENT2);
                        tabLayout.getTabAt(FRAGMENT1).setIcon(R.drawable.baseline_home_24_black);
                        tabLayout.getTabAt(FRAGMENT2).setIcon(R.drawable.baseline_search_24);
                        tabLayout.getTabAt(FRAGMENT3).setIcon(R.drawable.ic_baseline_person_24_black);

                        break;
                    case FRAGMENT3:
                        callFragment(FRAGMENT3);
                        tabLayout.getTabAt(FRAGMENT1).setIcon(R.drawable.baseline_home_24_black);
                        tabLayout.getTabAt(FRAGMENT2).setIcon(R.drawable.baseline_search_24_black);
                        tabLayout.getTabAt(FRAGMENT3).setIcon(R.drawable.ic_baseline_person_24);
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
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
    protected void onResume() {
        super.onResume();
        Log.d("fsdgsdfg", "액티비티 Resume");
        Intent intentFromFcm = getIntent();
        if (intentFromFcm.getBooleanExtra("fcm", false)) {
            Log.d("getFCM", "getFCM");
            isRecieved = true;
            forFcm.getRecievedData(new RecievedUserData(intentFromFcm.getIntExtra("recipientNumber", 0)));
        } else {
            Log.d("getFCM", "NoGetFCM");
            forFcm.getRecievedData(new RecievedUserData(currentUser.getUserNumber()));
        }
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
                Bundle bundle = new Bundle();
                MainPage mainPage = new MainPage().newInstance();
//                if(isRecieved){
                bundle.putString("jsonArray", jsonArray + "");
                mainPage.setArguments(bundle);
//                }
                transaction.replace(R.id.main_container, mainPage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case FRAGMENT2:
                // '프래그먼트2' 호출
                BoardPage_Main boardPage_main = new BoardPage_Main().newInstance();
                Bundle boardBundle = new Bundle();
                boardBundle.putSerializable("currentUserInfo", currentUser);
                boardPage_main.setArguments(boardBundle);
                transaction.replace(R.id.main_container, boardPage_main);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case FRAGMENT3:
                // '프래그먼트3' 호출
                writePage = new WritePage().newInstance();
                Bundle writeBundle = new Bundle();
                writeBundle.putSerializable("currentUserInfo", currentUser);
                writePage.setArguments(writeBundle);
                transaction.replace(R.id.create_page_container, writePage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();

                break;
            case FRAGMENT4:
                // '프래그먼트4' 호출
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
        WritePage writePage = new WritePage().newInstance();
        transaction.remove(writePage);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void postingBtClick() {
        slidingUpPanelLayout.setPanelHeight(0);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        callFragment(FRAGMENT2);
    }

    @Override
    public void writeBtClick() {
        callFragment(FRAGMENT3);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public void backBtClick(Fragment fragment) {
        callFragment(FRAGMENT1);
    }


    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        Gson gson = new Gson();
        currentUser = gson.fromJson(jsonArray.get(0).toString(), User.class);
        // forFcm.getRecievedData(new RecievedUserData(currentUser.getUserNumber()));
    }


    @Override
    public void getRecievedData(JSONArray jsonArray) throws JSONException {
        this.jsonArray = jsonArray;
        callFragment(FRAGMENT1);

    }


}

