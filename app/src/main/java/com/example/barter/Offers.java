package com.example.barter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TableLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Offers extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    User user;
    Bundle bundle;

    Offers offers;
    ArrayList<OfferObject> offersArrayList;
    ArrayList<TransactionObject> transactionObjectArrayList;
    OffersAdapter offersAdapter;
    TransactionAdapter transactionAdapter;
    RecyclerView recyclerView;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    Button bttn_myoffers;
    Button bttn_history;

    public Offers() {

    }


    public static Offers newInstance(String param1, String param2) {
        Offers fragment = new Offers();
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

        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        bundle = getArguments();
        user = (User) bundle.getSerializable("userInfo");
        bundle.putSerializable("userInfo",user);

        transactionObjectArrayList = new ArrayList<>();
        offersArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        offersAdapter = new OffersAdapter(getContext(),offersArrayList, user);

        getOffers();


        bttn_myoffers = view.findViewById(R.id.bttn_myoffers);
        bttn_myoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyOffers();
            }
        });

        bttn_history = view.findViewById(R.id.bttn_history);
        bttn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionObject transactionObject;

                 transactionAdapter = new TransactionAdapter(getContext(),transactionObjectArrayList);
                getTransaction();
            }
        });
        return view;
    }

    public void getOffers(){
        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidGetOffers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("Offers");
                            offersArrayList.clear();
                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                offersArrayList.add(new OfferObject(
                                        Integer.parseInt(jObject.getString("offerid")),
                                        Integer.parseInt(jObject.getString("listingid")),
                                        Integer.parseInt(jObject.getString("accountid")),
                                        Integer.parseInt(jObject.getString("image_id")),
                                        jObject.getString("productname"),
                                        jObject.getString("listing_details"),
                                        jObject.getString("dateoffered"),
                                        jObject.getString("offerstatus"),
                                        jObject.getString("phonenumber"),
                                        jObject.getString("firstname"),
                                        jObject.getString("middlename"),
                                        jObject.getString("lastname"),
                                        jObject.getString("userimage"),
                                        jObject.getString("image_url")
                                ));

                            }

                            recyclerView.setAdapter(offersAdapter);



                        } catch (JSONException e) {

                            e.printStackTrace();
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
                HashMap<String, String> map = new HashMap<>();

                map.put("accountid", String.valueOf(user.getAccountid()));

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getMyOffers(){
        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidGetUserOffers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("Offers");

                            offersArrayList.clear();
                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                offersArrayList.add(new OfferObject(
                                        Integer.parseInt(jObject.getString("offerid")),
                                        Integer.parseInt(jObject.getString("listingid")),
                                        Integer.parseInt(jObject.getString("accountid")),
                                        Integer.parseInt(jObject.getString("image_id")),
                                        jObject.getString("productname"),
                                        jObject.getString("listing_details"),
                                        jObject.getString("dateoffered"),
                                        jObject.getString("offerstatus"),
                                        jObject.getString("phonenumber"),
                                        jObject.getString("firstname"),
                                        jObject.getString("middlename"),
                                        jObject.getString("lastname"),
                                        jObject.getString("userimage"),
                                        jObject.getString("image_url")
                                ));

                            }

                            recyclerView.setAdapter(offersAdapter);



                        } catch (JSONException e) {

                            e.printStackTrace();
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
                HashMap<String, String> map = new HashMap<>();

                map.put("accountid", String.valueOf(user.getAccountid()));

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getTransaction(){

        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidGetTransaction.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("Transactions");

                            offersArrayList.clear();
                            transactionObjectArrayList.clear();
                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                transactionObjectArrayList.add(new TransactionObject(
                                        Integer.parseInt(jObject.getString("transactionid")),
                                        Integer.parseInt(jObject.getString("listingid")),
                                        Integer.parseInt(jObject.getString("offerid")),
                                        jObject.getString("transactiondate"),
                                        jObject.getString("product_name"),
                                        jObject.getString("productname")
                                ));

                            }

                            recyclerView.setAdapter(transactionAdapter);



                        } catch (JSONException e) {

                            e.printStackTrace();
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
                HashMap<String, String> map = new HashMap<>();

                map.put("accountid", String.valueOf(user.getAccountid()));

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}