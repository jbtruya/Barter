package com.example.barter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    ArrayList<Listing> listings;

    String imageURL = "https://xototlprojects.com/AndroidPHP/";

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView listing_image;
        ImageView userimage;
        TextView tv_product_name;
        TextView tv_listing_details;
        TextView tv_authorname;
        TextView tv_datelisted;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            listing_image = itemView.findViewById(R.id.imageView_listingImage);
            userimage = itemView.findViewById(R.id.imageView_userimage);
            tv_listing_details = itemView.findViewById(R.id.tv_listing_details);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_authorname = itemView.findViewById(R.id.tv_authorname);
            tv_datelisted = itemView.findViewById(R.id.tv_datelisted);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    ListingAdapter(Context context,ArrayList<Listing> listings){
            this.layoutInflater =LayoutInflater.from(context);
            this.listings = listings;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_listings,parent,false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String product_name = String.valueOf(listings.get(position).getProduct_name());
        String listing_details = String.valueOf(listings.get(position).getListing_details());
        String imageLocation = String.valueOf(listings.get(position).getImageURL());
        String userimageLocation = String.valueOf(listings.get(position).getUserimage());
        String datelisted = String.valueOf(listings.get(position).getDatelisted().replace(" ","\n"));
        String authorname = String.valueOf(listings.get(position).getFirstname())+" "+String.valueOf(listings.get(position).getMiddlename())+" "+String.valueOf(listings.get(position).getLastname());

        holder.tv_product_name.setText(product_name);
        Picasso.get().load(imageURL+imageLocation).resize(250,250).into(holder.listing_image);
        Picasso.get().load(imageURL+userimageLocation).resize(37,30).into(holder.userimage);
        holder.tv_listing_details.setText(listing_details);
        holder.tv_authorname.setText(authorname);
        holder.tv_datelisted.setText(datelisted);



    }

    @Override
    public int getItemCount() {
        return listings.size();
    }


}
