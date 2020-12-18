package com.example.barter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ViewListing extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    TextView tv_authorname;
    TextView tv_datelisted;
    TextView tv_productname;
    TextView tv_listingdetails;

    ImageView userimage;
    ImageView listingimage;

    String imageURL = "https://xototlprojects.com/AndroidPHP/";
    Listing listing;
    Bundle bundle;
    User user;

    Button bttn_editListing;
    Button bttn_Offer;
    ImageView imageView_selectedImage;

    private static final int PICK_IMAGE = 111;
    Bitmap bitmap;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String currentDate;

    public ViewListing() {

    }

    public static ViewListing newInstance(String param1, String param2) {
        ViewListing fragment = new ViewListing();
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

        View view = inflater.inflate(R.layout.fragment_view_listing, container, false);
        bundle = getArguments();
        Bundle listingBundle = bundle.getBundle("listingInfo");
        Bundle userBundle = bundle.getBundle("userInfo");
        listing = (Listing) listingBundle.getSerializable("listingInfo");
        user = (User) userBundle.getSerializable("userInfo");


        userimage = view.findViewById(R.id.imageView_userimage);
        listingimage = view.findViewById(R.id.imageView_listingImage);
        tv_authorname = view.findViewById(R.id.tv_authorname);
        tv_productname = view.findViewById(R.id.tv_productname);
        tv_listingdetails = view.findViewById(R.id.tv_listingdetails);
        tv_datelisted = view.findViewById(R.id.tv_datelisted);
        bttn_editListing = view.findViewById(R.id.bttn_editListing);
        bttn_Offer = view.findViewById(R.id.bttn_Offer);

        Picasso.get().load(imageURL+listing.getImageURL()).resize(400,230).into(listingimage);
        Picasso.get().load(imageURL+listing.getUserimage()).resize(37,30).into(userimage);

        tv_authorname.setText(listing.getFirstname()+" "+listing.getMiddlename()+" "+listing.getLastname());
        tv_datelisted.setText(listing.getDatelisted().replace(" ","\n"));
        tv_productname.setText(listing.getProduct_name());
        tv_listingdetails.setText(listing.getListing_details());

        if(user.getAccountid() != listing.getAccountid()){
            bttn_editListing.setText("Message");
        }else{
            bttn_editListing.setVisibility(view.VISIBLE);
            bttn_Offer.setVisibility(view.INVISIBLE);
        }

        // START Phone back button
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){

                    if(listing.getGoBackTo().equals("listingFeed")){
                        getFragmentManager().popBackStack("listingFeed", 0);
                    }else{
                        getFragmentManager().popBackStack("userProfile", 0);
                    }

                }

                return false;
            }
        });
        // END Phone back button

        bttn_editListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bttn_editListing.getText().toString().equals("Edit Listing")){
                    final Dialog editListingDialog = editListing(listing);
                    editListingDialog.show();
                }else{
                    showUserContactDialog();
                }

            }
        });

        bttn_Offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog offerDialog = showOfferDialog();
                offerDialog.show();
            }
        });
        return view;
    }

    public Dialog editListing(final Listing listing){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editlisting);

        imageView_selectedImage = dialog.findViewById(R.id.imageView_selectedImage);

        Button bttn_selectImage = dialog.findViewById(R.id.bttn_selectImage);
        Button bttn_editListing = dialog.findViewById(R.id.bttn_sendOffer);
        Button bttn_deleteListing = dialog.findViewById(R.id.bttn_deleteListing);

        final TextInputLayout edt_productname = dialog.findViewById(R.id.edt_productName);
        final TextInputLayout edt_listingdetails = dialog.findViewById(R.id.edt_listingDetails);


        Picasso.get().load(imageURL+listing.getImageURL()).into(imageView_selectedImage);
        edt_productname.getEditText().setText(listing.getProduct_name());
        edt_listingdetails.getEditText().setText(listing.getListing_details());

        bttn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"), PICK_IMAGE);
            }
        });

        bttn_editListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListing(edt_productname.getEditText().getText().toString(),edt_listingdetails.getEditText().getText().toString(),listing);
            }
        });

        bttn_deleteListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListing(listing);
                dialog.hide();
                Bundle newUserInfoBundle = new Bundle();
                Listingfeed listingfeed = new Listingfeed();
                newUserInfoBundle.putSerializable("userInfo",user);
                listingfeed.setArguments(newUserInfoBundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_display,listingfeed).commit();
            }
        });

        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            Uri imagePath = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);

                imageView_selectedImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void updateListing(final String product_name, final String listing_details, final Listing listing){

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidUpdateListing.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getContext(),"Listing Updated!",Toast.LENGTH_LONG).show();
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
                    map.put("listing_id",String.valueOf(listing.getListing_id()));

                    return map;
                }
            };
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }catch (NullPointerException errmsg){
            stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidUpdateListingWithoutPhoto.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getContext(),"Listing Updated!",Toast.LENGTH_LONG).show();
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

                    map.put("product_name", product_name);
                    map.put("listing_details", listing_details);
                    map.put("listing_id",String.valueOf(listing.getListing_id()));

                    return map;
                }
            };
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }

    }

    public void deleteListing(final Listing listing){
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidDeleteListing.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getContext(),"Listing Deleted!",Toast.LENGTH_LONG).show();
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

                map.put("listing_id",String.valueOf(listing.getListing_id()));

                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public Dialog showUserContactDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_usercontact);

        TextView tv_username = dialog.findViewById(R.id.tv_username);
        TextView tv_phonenumber = dialog.findViewById(R.id.tv_phonenumber);
        ImageView imageView_userimage = dialog.findViewById(R.id.imageView_userimage);
        tv_username.setText(listing.getFirstname()+" "+listing.getMiddlename()+" "+listing.getLastname());
        tv_phonenumber.setText(listing.getPhonenumber());

        Picasso.get().load(imageURL+listing.getUserimage()).resize(37,37).into(imageView_userimage);

        final String phoneNumber = tv_phonenumber.getText().toString();

        tv_phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (android.content.ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("phonenumber",phoneNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getContext(),"Number Copied.",Toast.LENGTH_LONG).show();
            }
        });



        dialog.show();
        return dialog;
    }

    public Dialog showOfferDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sendoffer);


        imageView_selectedImage = dialog.findViewById(R.id.imageView_selectedImage);

        Button bttn_selectImage = dialog.findViewById(R.id.bttn_selectImage);
        Button bttn_sendOffer = dialog.findViewById(R.id.bttn_sendOffer);

        final TextInputLayout edt_productname = dialog.findViewById(R.id.edt_productName);
        final TextInputLayout edt_listingdetails = dialog.findViewById(R.id.edt_listingDetails);


        bttn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"), PICK_IMAGE);
            }
        });


        bttn_sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate = dateFormat.format(new Date());
                createOffer(edt_productname.getEditText().getText().toString(),edt_listingdetails.getEditText().getText().toString());
            }
        });

        return dialog;
    }

    public void createOffer(final String product_name, final String listing_details){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/createOfferAndroid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Offer Created!",Toast.LENGTH_LONG).show();
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
                map.put("accountid",String.valueOf(user.getAccountid()));
                map.put("dateoffered",currentDate);
                map.put("listing_id", String.valueOf(listing.getListing_id()));

                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}