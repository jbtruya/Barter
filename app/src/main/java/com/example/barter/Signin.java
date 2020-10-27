package com.example.barter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Signin extends Fragment {


    Button bttn_signin;
    Button bttn_signup;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Signin() {

    }


    public static Signin newInstance(String param1, String param2) {
        Signin fragment = new Signin();
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

        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        // START Initialize views
        bttn_signin = view.findViewById(R.id.bttn_signin);
        bttn_signup = view.findViewById(R.id.bttn_signup);
        // END Initialize views

        // START initial animation
        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(bttn_signin);

        YoYo.with(Techniques.BounceInUp)
                .delay(150)
                .duration(1500)
                .playOn(bttn_signup);
        // END initial animation


        // START Open Sign up Fragment
        bttn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager()
                       .beginTransaction()
                        .replace(R.id.frame_mainDisplay, new Signup())
                        .addToBackStack(null)
                        .commit();


            }
        });
        // END Open Sign in Fragment
        //START Open Sign in Fragment
        bttn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_mainDisplay, new SigninSubmitInfo())
                        .addToBackStack("SigninSubmitInfo")
                        .commit();
            }
        });
        //END Open Sig in Fragment
        return view;
    }
}