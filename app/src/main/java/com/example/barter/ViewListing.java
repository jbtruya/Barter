package com.example.barter;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


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
        listing = (Listing) bundle.getSerializable("listingInfo");

        userimage = view.findViewById(R.id.imageView_userimage);
        listingimage = view.findViewById(R.id.imageView_listingImage);
        tv_authorname = view.findViewById(R.id.tv_authorname);
        tv_productname = view.findViewById(R.id.tv_productname);
        tv_listingdetails = view.findViewById(R.id.tv_listingdetails);
        tv_datelisted = view.findViewById(R.id.tv_datelisted);

        Picasso.get().load(imageURL+listing.getImageURL()).into(listingimage);
        Picasso.get().load(imageURL+listing.getUserimage()).resize(37,30).into(userimage);

        tv_authorname.setText(listing.getFirstname()+" "+listing.getMiddlename()+" "+listing.getLastname());
        tv_datelisted.setText(listing.getDatelisted().replace(" ","\n"));
        tv_productname.setText(listing.getProduct_name());
        tv_listingdetails.setText(listing.getListing_details());



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
        return view;
    }

}