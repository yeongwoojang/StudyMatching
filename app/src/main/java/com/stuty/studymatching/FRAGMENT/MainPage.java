package com.stuty.studymatching.FRAGMENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.stuty.studymatching.OBJECT.ReceivedData;
import com.stuty.studymatching.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class MainPage extends Fragment {

    private Context mContext = null;
    private LogoutListener logoutListener;


    private DrawerLayout mDrawerLayout;
    private ImageButton menuBt;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView testText;
    public MainPage newInstance(){return new MainPage();}

    private String sender;
    private JSONArray jsonArray;
    private List<ReceivedData> receivedDataList = new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logoutListener = (LogoutListener) context;
        if(getArguments()!=null){
            Gson gson = new Gson();
            try {
                jsonArray = new JSONArray(getArguments().getString("jsonArray"));
                Toast.makeText(context,jsonArray+"",Toast.LENGTH_SHORT).show();
                for(int i=0;i<jsonArray.length();i++){
                    receivedDataList.add(gson.fromJson(jsonArray.get(i).toString(),ReceivedData.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,sender,Toast.LENGTH_SHORT).show();
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
        navigationView = rootView.findViewById(R.id.nav_view);

        testText = rootView.findViewById(R.id.testText);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(receivedDataList.size()>0){
//            testText.setText(receivedDataList.get(receivedDataList.size()-1).getSender());
            testText.setText("sdgsdgsdf");
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

}
