package com.example.barter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class userprofile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int PICK_IMAGE = 111;
    Bitmap bitmap;

    User user;
    Bundle bundle;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    Button bttn_editprofile;
    ImageButton bttn_edituserimage;
    ImageView userimage;
    TextView tv_username;
    TextView tv_userhandler;


    String imageURL = "https://xototlprojects.com/AndroidPHP/";
    public userprofile() {

    }
    public static userprofile newInstance(String param1, String param2) {
        userprofile fragment = new userprofile();
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

        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);

        bundle = getArguments();
        user = (User) bundle.getSerializable("userInfo");
        getLatestUserInfo();

        tv_username = view.findViewById(R.id.tv_username);
        tv_userhandler = view.findViewById(R.id.tv_userhandler);
        userimage = view.findViewById(R.id.imageView_userimage);

        bttn_editprofile = view.findViewById(R.id.bttn_editprofile);
        bttn_edituserimage = view.findViewById(R.id.bttn_edituserimage);
        if(!user.getUserimage().equals("0"))
        {
            Picasso.get().load(imageURL+user.getUserimage()).resize(150,150).into(userimage);
        }



        updateViews();
        YoYo.with(Techniques.Wobble)
                .duration(1600)
                .repeat(Animation.INFINITE)
                .playOn(bttn_editprofile);

        bttn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bttn_edituserimage.getVisibility() == View.INVISIBLE){
                    bttn_edituserimage.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.Wobble)
                            .duration(1200)
                            .repeat(Animation.INFINITE)
                            .playOn(bttn_edituserimage);
                }else{
                    bttn_edituserimage.setVisibility(View.INVISIBLE);
                }
            }
        });

        bttn_edituserimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bttn_edituserimagetag = String.valueOf(bttn_edituserimage.getTag());

                if(bttn_edituserimagetag.equals("confirm")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirm");
                    builder.setMessage("Use this as your profile picture?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uploadUserImage();
                            bttn_edituserimage.setImageResource(R.drawable.ic_baseline_edit_24);
                            bttn_edituserimage.setTag("setImage");

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editUseriamge();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    editUseriamge();
                }
            }
        });
        return view;
    }

    public void updateViews(){
        String username = user.getFname()+" "+user.getMname()+" "+user.getLname();
        String userhandler = "@"+user.getUsername();

        tv_username.setText(username);
        tv_userhandler.setText(userhandler);
    }
    public void editUseriamge(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"Select Image"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imagePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);
                bitmap = Bitmap.createScaledBitmap(bitmap,150,150,false);
                userimage.setImageBitmap(bitmap);
                bttn_edituserimage.setImageResource(R.drawable.ic_baseline_done_24);
                bttn_edituserimage.setTag("confirm");
                YoYo.with(Techniques.Wobble)
                        .duration(1500)
                        .repeat(Animation.INFINITE)
                        .playOn(bttn_edituserimage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadUserImage(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidUpdateUserimage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Image Updated!",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("image",imageString);
                map.put("accountid", String.valueOf(user.getAccountid()));

                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    public void getLatestUserInfo(){
        requestQueue = Volley.newRequestQueue(getContext());

        stringRequest = new StringRequest(Request.Method.POST,
                "https://xototlprojects.com/AndroidPHP/androidGetUserProfileImage.php"
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

                }catch (JSONException errmsg){
                    Toast.makeText(getContext(), "Inavlid Details", Toast.LENGTH_LONG).show();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "AN Error Occurred!", Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();

                map.put("accountid", String.valueOf(user.getAccountid()));

                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}