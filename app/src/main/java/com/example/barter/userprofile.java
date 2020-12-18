package com.example.barter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.ArrayList;
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

    RecyclerView recyclerView;

    String imageURL = "https://xototlprojects.com/AndroidPHP/";

    ListingAdapter listingAdapter;
    Listing listing;
    ArrayList<Listing> listings;
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

        //START GET USER INFO
        bundle = getArguments();
        user = (User) bundle.getSerializable("userInfo");
        getLatestUserInfo();
        //END GET USER INFO

        //START INITIALIZE VIEWS
        tv_username = view.findViewById(R.id.tv_username);
        tv_userhandler = view.findViewById(R.id.tv_userhandler);
        userimage = view.findViewById(R.id.imageView_userimage);

        bttn_editprofile = view.findViewById(R.id.bttn_editprofile);
        bttn_edituserimage = view.findViewById(R.id.bttn_edituserimage);

        recyclerView = view.findViewById(R.id.recyclerView);
        //END INITIALIZE VIEWS

        //START CHECK IF USER HAS A PROFILE PICTURE
        if(!user.getUserimage().equals("0"))
        {
            Picasso.get().load(imageURL+user.getUserimage()).resize(150,150).into(userimage);
        }else{

        }
        //END CHECK IF USER HAS A PROFILE PICTURE


        listings = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingAdapter = new ListingAdapter(getContext(),listings,user);

        loadListingInfo();
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


        listingAdapter.setOnItemClickListener(new ListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle mainBundle = new Bundle();

                Bundle listingInfoBundle = new Bundle();
                listing = new Listing(listings.get(position).getImageURL(),
                        listings.get(position).getProduct_name(),
                        listings.get(position).getListing_details(),
                        listings.get(position).getAccountid(),
                        listings.get(position).getListing_id(),
                        listings.get(position).getImage_id(),
                        listings.get(position).getFirstname(),
                        listings.get(position).getMiddlename(),
                        listings.get(position).getLastname(),
                        listings.get(position).getDatelisted(),
                        listings.get(position).getUserimage(),
                        "listingFeed",
                        listings.get(position).getPhonenumber(),
                        0);

                ViewListing viewListing = new ViewListing();

                listingInfoBundle.putSerializable("listingInfo",listing);

                mainBundle.putBundle("userInfo",bundle);
                mainBundle.putBundle("listingInfo",listingInfoBundle);

                viewListing.setArguments(mainBundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_display,viewListing)
                        .addToBackStack("listingFeed")
                        .commit();
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

    private void loadListingInfo(){
        final Dialog Progressdialog = showLoadingFeedDialog();

        Progressdialog.show();

        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidGetUserlistingInfo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("listingInfo");

                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                listings.add(
                                        new Listing(
                                                jObject.getString("image_url"),
                                                jObject.getString("product_name"),
                                                jObject.getString("listing_details"),
                                                Integer.parseInt(jObject.getString("accountid")),
                                                Integer.parseInt(jObject.getString("listing_id")),
                                                Integer.parseInt(jObject.getString("image_id")),
                                                jObject.getString("firstname"),
                                                jObject.getString("middlename"),
                                                jObject.getString("lastname"),
                                                jObject.getString("dateListed"),
                                                jObject.getString("userimage"),
                                                jObject.getString("phonenumber")

                                        ));

                            }

                            recyclerView.setAdapter(listingAdapter);

                            Progressdialog.hide();
                            Progressdialog.dismiss();

                        } catch (JSONException e) {
                            Progressdialog.hide();
                            Progressdialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      Progressdialog.hide();
                       Progressdialog.dismiss();

                        Toast.makeText(getContext(),"Error when loading feed.",Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("accountid",String.valueOf(user.getAccountid()));
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public Dialog showLoadingFeedDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_listingfeed_dialog);

        dialog.setCancelable(false);
        return dialog;
    }
}