package com.example.barter;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    User user;
    Bundle bundle;
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

        user = new User();
        bundle = new Bundle();

        //Initialize Views

        edt_username = view.findViewById(R.id.edt_username);
        edt_password = view.findViewById(R.id.edt_password);

        bttn_submit_SignupInfo = view.findViewById(R.id.bttn_signin);




        //START Submit signin info
        bttn_submit_SignupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SignInUser(edt_username.getEditText().getText().toString(),edt_password.getEditText().getText().toString());
            }
        });
        //END Submit signin info

        return view;
    }


    public void SignInUser(final String username, final String password){
       final Dialog Progressdialog = showSigningInDialog();

        Progressdialog.show();

        requestQueue = Volley.newRequestQueue(getContext());

        stringRequest = new StringRequest(Request.Method.POST,
                "https://xototlprojects.com/AndroidPHP/androidGetUserInfo.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("userinfo");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jObject = jsonArray.getJSONObject(i);

                        user.setAccountid(Integer.valueOf(jObject.getString("accountid")));
                        user.setUsername(jObject.getString("username"));
                        user.setUserimage(jObject.getString("userimage"));
                        user.setFname(jObject.getString("firstname"));
                        user.setMname(jObject.getString("middlename"));
                        user.setLname(jObject.getString("lastname"));
                    }
                    Progressdialog.hide();
                    Progressdialog.dismiss();
                    BarterHome barterHome = new BarterHome();
                    bundle.putSerializable("userInfo", user);
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    barterHome.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_mainDisplay, barterHome)
                            .commit();

                }catch (JSONException errmsg){
                    Progressdialog.hide();
                    Progressdialog.dismiss();
                    Toast.makeText(getContext(), "Inavlid Details", Toast.LENGTH_LONG).show();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "AN Error Occurred!", Toast.LENGTH_LONG).show();
                Progressdialog.hide();
                Progressdialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();

                map.put("username", username);
                map.put("password", password);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    public Dialog showSigningInDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.signin_progress_dialog);

        dialog.setCancelable(false);
        return dialog;

        }

}
