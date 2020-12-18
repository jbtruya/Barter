package com.example.barter;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;


public class SearchListing extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    EditText edt_searchProductName;
    ImageButton imageButton_search;
    RecyclerView recyclerView;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    ListingAdapter listingAdapter;
    Listing listing;
    ArrayList<Listing> listings;
    Bundle bundle;

    User user;

    public SearchListing() {

    }

    public static SearchListing newInstance(String param1, String param2) {
        SearchListing fragment = new SearchListing();
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

        View view = inflater.inflate(R.layout.fragment_search_listing, container, false);

        edt_searchProductName = view.findViewById(R.id.edt_searchProductName);
        imageButton_search = view.findViewById(R.id.imageButton_search);

        bundle = new Bundle();
        bundle = getArguments();
        user = (User) bundle.getSerializable("userInfo");

        listings = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_Listing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingAdapter = new ListingAdapter(getContext(),listings,user);


        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_searchProductName.getText().toString().isEmpty()){
                    loadListingInfo(edt_searchProductName.getText().toString());
                }else{
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

    private void loadListingInfo(final String productNameSearch){

        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidSearchListing.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("listingInfo");

                            listings.clear();

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

                map.put("productName", productNameSearch);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }


}