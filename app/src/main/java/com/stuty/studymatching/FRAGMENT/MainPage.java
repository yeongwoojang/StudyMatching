package com.stuty.studymatching.FRAGMENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.stuty.studymatching.ACTIVITY.RequestListActivity;
import com.stuty.studymatching.OBJECT.RecievedData;
import com.stuty.studymatching.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class MainPage extends Fragment {

    private Context mContext = null;
    private LogoutListener logoutListener;


    private DrawerLayout mDrawerLayout;
    private ImageButton menuBt,alertBt;
    private Toolbar toolbar;
    private NavigationView navigationView;

    public MainPage newInstance() {
        return new MainPage();
    }

    private String sender;
    private JSONArray jsonArray;
    private List<RecievedData> recievedDataList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        logoutListener = (LogoutListener) context;
        if (getArguments() != null) {
            Gson gson = new Gson();
            try {
                jsonArray = new JSONArray(getArguments().getString("jsonArray"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    recievedDataList.add(gson.fromJson(jsonArray.get(i).toString(), RecievedData.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
        alertBt = rootView.findViewById(R.id.alert_image);
        navigationView = rootView.findViewById(R.id.nav_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        for(int i=0;i<recievedDataList.size();i++){
            if(recievedDataList.get(i).getCheckRequest()==0){
                alertBt.setVisibility(View.VISIBLE);
            }
        }
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

                } else if (id ==R.id.request_list) {
                    Intent intent = new Intent(mContext, RequestListActivity.class);
                    intent.putExtra("jsonArray",jsonArray.toString());
                    startActivity(intent);
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


    public interface LogoutListener {
        void successLogout();
    }

}
