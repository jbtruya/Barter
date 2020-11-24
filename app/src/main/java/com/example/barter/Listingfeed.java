package com.example.barter;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listingfeed extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    ListingAdapter listingAdapter;
    ArrayList<Listing> listings;

    public Listingfeed() {

    }

    public static Listingfeed newInstance(String param1, String param2) {
        Listingfeed fragment = new Listingfeed();
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

        View view = inflater.inflate(R.layout.fragment_listingfeed, container, false);

        listings = new ArrayList<>();
        recyclerView = view.findViewById(R.id.listingfeed_recycler);

        loadListingInfo();
        return view;
    }

    private void loadListingInfo(){

        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.GET, "https://xototlprojects.com/AndroidPHP/androidGetlistingInfo.php",
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
                                                Integer.parseInt(jObject.getString("image_id"))
                                        ));

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            listingAdapter = new ListingAdapter(getContext(),listings);
                            recyclerView.setAdapter(listingAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }
}