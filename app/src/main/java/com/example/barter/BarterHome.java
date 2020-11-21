package com.example.barter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BarterHome extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    BottomNavigationView bottomNavigationView;


    User user;
    Bundle bundle;
    public BarterHome() {

    }

    public static BarterHome newInstance(String param1, String param2) {
        BarterHome fragment = new BarterHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_barter_home, container, false);

        bundle = getArguments();
        user = (User) bundle.getSerializable("userInfo");


        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        return true;

                    case R.id.menu_add:
                        addItem addItem = new addItem();
                        bundle.putSerializable("userInfo",user);
                        addItem.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.frame_display, addItem).commit();
                        return true;

                    case R.id.menu_profile:
                        return true;

                }

                return false;
            }
        });

        return view;
    }
}