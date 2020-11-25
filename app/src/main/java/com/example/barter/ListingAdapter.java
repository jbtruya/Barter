package com.example.barter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    ArrayList<Listing> listings;

    String imageURL = "https://xototlprojects.com/AndroidPHP/";

    ListingAdapter(Context context,ArrayList<Listing> listings){
            this.layoutInflater =LayoutInflater.from(context);
            this.listings = listings;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_listings,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String product_name = String.valueOf(listings.get(position).getProduct_name());
        String listing_details = String.valueOf(listings.get(position).getListing_details());
        String imageLocation = String.valueOf(listings.get(position).getImageURL());
        holder.tv_product_name.setText(product_name);
        Picasso.get().load(imageURL+imageLocation).resize(250,250).into(holder.listing_image);
        holder.tv_listing_details.setText(listing_details);


    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView listing_image;
        TextView tv_product_name;
        TextView tv_listing_details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listing_image = itemView.findViewById(R.id.imageView_listingImage);
            tv_listing_details = itemView.findViewById(R.id.tv_listing_details);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
        }
    }
}
