package com.example.barter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class addItem extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private static final int PICK_IMAGE = 111;
    Bitmap bitmap;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    Button bttn_selectImage;
    Button bttn_addListing;
    ImageView selectedImage;

    TextInputLayout edt_product_name;
    TextInputLayout edt_listing_details;


    public addItem() {
        // Required empty public constructor
    }

    public static addItem newInstance(String param1, String param2) {
        addItem fragment = new addItem();
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

        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        bttn_selectImage = view.findViewById(R.id.bttn_selectImage);
        bttn_addListing = view.findViewById(R.id.bttn_addListing);
        selectedImage = view.findViewById(R.id.imageView_selectedImage);

        edt_product_name = view.findViewById(R.id.edt_productName);
        edt_listing_details = view.findViewById(R.id.edt_lisingDetails);

        bttn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"), PICK_IMAGE);
            }
        });

        bttn_addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addListing(edt_product_name.getEditText().getText().toString(),edt_listing_details.getEditText().getText().toString());

            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            Uri imagePath = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);

                selectedImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void addListing(final String product_name, final String listing_details) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/addListingAndroid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Listing Created!",Toast.LENGTH_LONG).show();
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
                map.put("product_name", product_name);
                map.put("listing_details", listing_details);

                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}