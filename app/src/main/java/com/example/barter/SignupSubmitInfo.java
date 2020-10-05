package com.example.barter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;


public class SignupSubmitInfo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    TextInputLayout edt_username;
    TextInputLayout edt_password;
    TextInputLayout edt_confirmPass;

    Button bttn_signup;


    public SignupSubmitInfo() {

    }

    public static SignupSubmitInfo newInstance(String param1, String param2) {
        SignupSubmitInfo fragment = new SignupSubmitInfo();
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

        View view = inflater.inflate(R.layout.fragment_signup_submit_info, container, false);

        //START INITIALIZE VIEWS
        edt_username = view.findViewById(R.id.edt_username);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirmPass = view.findViewById(R.id.edt_confirmpass);

        bttn_signup = view.findViewById(R.id.bttn_signup_submit);
        //END INITIALIZE VIEWS

        //START VIEW ANIMATIONS
        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(edt_username);

        YoYo.with(Techniques.BounceInUp)
                .delay(150)
                .duration(1500)
                .playOn(edt_password);

        YoYo.with(Techniques.BounceInUp)
                .delay(250)
                .duration(1500)
                .playOn(edt_confirmPass);

        YoYo.with(Techniques.BounceInUp)
                .delay(300)
                .duration(1500)
                .playOn(bttn_signup);
        //END VIEW ANIMATIONS

        //START RECEIVE CLASS OBJECT FROM Signup
            Bundle bundle = getArguments();
            final User newUser = (User)  bundle.getSerializable("newUser");
        //END RECEIVE CLASS OBJECT FROM Signup

        //START SUBMIT USER INFO / SIGN UP
        bttn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateUsername() | !validatePassword()){
                    return;
                }

                requestQueue = Volley.newRequestQueue(getContext());

                stringRequest = new StringRequest(Request.Method.POST,
                        "https://xototlprojects.com/AndroidPHP/registeruser.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(getContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> map =new HashMap<>();

                        map.put("username", edt_username.getEditText().getText().toString());
                        map.put("password", edt_password.getEditText().getText().toString());
                        map.put("firstname", newUser.getFname());
                        map.put("middlename", newUser.getMname());
                        map.put("lastname", newUser.getLname());
                        map.put("dateofbirth", newUser.getDob());
                        map.put("address", newUser.getAddress());

                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        //END SUBMIT USER INFO / SIGN UP


        //START para sa back button sa phone
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                      // getFragmentManager().beginTransaction()
                               // .replace(R.id.frame_mainDisplay, new Signup())
                                //.addToBackStack(null)
                               // .commit();

                        getFragmentManager().popBackStack("signUp", 0);
                    }

                    return false;
                }
            });
        //END para sa back button sa phone

        return view;
    }

    public boolean validateUsername(){
        if(edt_username.getEditText().getText().toString().isEmpty()){
            edt_username.setError("Field can't be empty");
                return false;
        }
        else{
            edt_username.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        if(edt_password.getEditText().getText().toString().isEmpty()){
            edt_password.setError("Field can't be empty");
            return false;
        }

         else if(edt_confirmPass.getEditText().getText().toString().isEmpty()){
            edt_password.setError(null);
            edt_confirmPass.setError("Field can't be empty");
            return false;
        }
         else if(!edt_password.getEditText().getText().toString().equals(edt_confirmPass.getEditText().getText().toString())){
            edt_password.setError(null);
            edt_confirmPass.setError("Password Doest not match");
            return false;
        }
         else{

            edt_confirmPass.setError(null);
            return true;
        }
    }
}