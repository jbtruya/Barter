package com.example.barter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    Context context;
    ArrayList<OfferObject> offersArrayList;
    User user;

    String imageURL = "https://xototlprojects.com/AndroidPHP/";

    RequestQueue requestQueue;
    StringRequest stringRequest;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String currentDate;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_offer_authorname;
        TextView tv_offer_date;
        TextView tv_offer_productname;
        TextView tv_offer_details;
        TextView tv_status;

        ImageView imageView_userimage2;
        ImageView imageView_offerImage;

        Button bttn_accept;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_offer_authorname = itemView.findViewById(R.id.tv_offer_authorname);
            tv_offer_date = itemView.findViewById(R.id.tv_offer_date);
            tv_offer_productname = itemView.findViewById(R.id.tv_offer_productname);
            tv_offer_details = itemView.findViewById(R.id.tv_offer_details);
            tv_status = itemView.findViewById(R.id.tv_status);

            imageView_offerImage = itemView.findViewById(R.id.imageView_offerImage);
            imageView_userimage2 = itemView.findViewById(R.id.imageView_userimage2);

            bttn_accept = itemView.findViewById(R.id.bttn_accept);
        }
    }

    OffersAdapter(Context context,ArrayList<OfferObject> offersArrayList, User user){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.offersArrayList = offersArrayList;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_offers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.tv_offer_authorname.setText(offersArrayList.get(position).getFirstname()+" "+offersArrayList.get(position).getMiddlename()+" "+offersArrayList.get(position).getLastname());
            holder.tv_offer_productname.setText(offersArrayList.get(position).getProductname());
            holder.tv_offer_details.setText(offersArrayList.get(position).getListingdetails());
            holder.tv_offer_date.setText(offersArrayList.get(position).getDateoffered().replace(" ","\n"));

            Picasso.get().load(imageURL+offersArrayList.get(position).getUserimage()).resize(37,30).into(holder.imageView_userimage2);
            Picasso.get().load(imageURL+offersArrayList.get(position).getImage_url()).resize(250,250).into(holder.imageView_offerImage);

            holder.tv_status.setText(offersArrayList.get(position).getOfferstatus());


            if(offersArrayList.get(position).getAccountid() == user.getAccountid()){
                holder.bttn_accept.setText("Remove Offer");
                holder.bttn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOffer(offersArrayList.get(position).getOfferid());
                    }
                });

            }

            else{
                holder.bttn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentDate = dateFormat.format(new Date());
                        acceptOffer(position,currentDate);

                    }
                });
            }


    }

    @Override
    public int getItemCount() {
        return offersArrayList.size();
    }

    public void acceptOffer(final int postion, final String currentDate){

        requestQueue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidAcceptOffer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"Offer accepted!",Toast.LENGTH_LONG).show();
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

                map.put("listingid", String.valueOf(offersArrayList.get(postion).getListingid()));
                map.put("offerid", String.valueOf(offersArrayList.get(postion).getOfferid()));
                map.put("datetime", currentDate);

                return map;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void deleteOffer(final int offerid){
        requestQueue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(Request.Method.POST, "https://xototlprojects.com/AndroidPHP/androidAcceptOffer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"Offer removed!",Toast.LENGTH_LONG).show();
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

                map.put("offerid", String.valueOf(offerid));

                return map;
            }
        };

        requestQueue.add(stringRequest);


    }
}
