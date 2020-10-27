package com.example.barter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class SigninSubmitInfo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    Button bttn_submit_SignupInfo;

    TextInputLayout edt_username;
    TextInputLayout edt_password;

    public SigninSubmitInfo() {

    }

    public static SigninSubmitInfo newInstance(String param1, String param2) {
        SigninSubmitInfo fragment = new SigninSubmitInfo();
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

        View view = inflater.inflate(R.layout.fragment_signin_submit_info, container, false);

        //Initialize Views
        edt_username = view.findViewById(R.id.edt_username);
        edt_password = view.findViewById(R.id.edt_password);

        bttn_submit_SignupInfo = view.findViewById(R.id.bttn_signin);




        //START Submit signin info
        bttn_submit_SignupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(getContext());

                stringRequest = new StringRequest(Request.Method.POST,
                        "https://xototlprojects.com/AndroidPHP/verifyUser.php"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("User Verified!")){
                            Toast.makeText(getContext(), "User Verified!", Toast.LENGTH_LONG).show();
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_mainDisplay, new BarterHome())
                                    .addToBackStack("BarterHome")
                                    .commit();
                        }else{
                            Toast.makeText(getContext(), "Invalid User!", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> map = new HashMap<>();

                        map.put("1username", edt_username.getEditText().getText().toString());
                        map.put("1password", edt_password.getEditText().getText().toString());

                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        //END Submit signin info
        return view;
    }
}