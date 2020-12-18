package com.example.barter;

import android.app.Application;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    ArrayList<Listing> listings;
    Listing listing;
    User user;

    Bundle bundle;
    String imageURL = "https://xototlprojects.com/AndroidPHP/";
    Context context;


    private static final int PICK_IMAGE = 111;
    Bitmap bitmap;

    RequestQueue requestQueue;
    StringRequest stringRequest;

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

        Button bttn_message;



        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            listing_image = itemView.findViewById(R.id.imageView_listingImage);
            userimage = itemView.findViewById(R.id.imageView_userimage);
            tv_listing_details = itemView.findViewById(R.id.tv_listing_details);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_authorname = itemView.findViewById(R.id.tv_authorname);
            tv_datelisted = itemView.findViewById(R.id.tv_datelisted);

            bttn_message = itemView.findViewById(R.id.bttn_message);

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


    ListingAdapter(Context context,ArrayList<Listing> listings,User user){
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.listings = listings;
            this.user = user;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_listings,parent,false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String product_name = String.valueOf(listings.get(position).getProduct_name());
        final String listing_details = String.valueOf(listings.get(position).getListing_details());
        String imageLocation = String.valueOf(listings.get(position).getImageURL());
        String userimageLocation = String.valueOf(listings.get(position).getUserimage());
        String datelisted = String.valueOf(listings.get(position).getDatelisted().replace(" ","\n"));
        final String authorname = String.valueOf(listings.get(position).getFirstname())+" "+String.valueOf(listings.get(position).getMiddlename())+" "+String.valueOf(listings.get(position).getLastname());

        holder.tv_product_name.setText(product_name);
        Picasso.get().load(imageURL+imageLocation).resize(250,250).into(holder.listing_image);
        Picasso.get().load(imageURL+userimageLocation).resize(37,30).into(holder.userimage);
        holder.tv_listing_details.setText(listing_details);
        holder.tv_authorname.setText(authorname);
        holder.tv_datelisted.setText(datelisted);



        if(listings.get(position).getAccountid() == user.getAccountid()){
            Drawable img = holder.bttn_message.getContext().getDrawable(R.drawable.ic_baseline_edit_24);
            holder.bttn_message.setText("Edit post");
            holder.bttn_message.setCompoundDrawablesWithIntrinsicBounds(img, null,null,null);

            holder.bttn_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listing = new Listing(
                            listings.get(position).getImageURL(),
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

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

                    Bundle mainBundle = new Bundle();
                    Bundle listingInfoBundle = new Bundle();
                    Bundle userInfoBundle = new Bundle();

                    listingInfoBundle.putSerializable("listingInfo",listing);
                    userInfoBundle.putSerializable("userInfo",user);

                    mainBundle.putBundle("userInfo",userInfoBundle);
                    mainBundle.putBundle("listingInfo",listingInfoBundle);

                    ViewListing viewListing = new ViewListing();
                   viewListing.setArguments(mainBundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_display,viewListing)
                            .addToBackStack("listingFeed")
                            .commit();
                }
            });

        }else{
            Drawable img = holder.bttn_message.getContext().getDrawable(R.drawable.ic_baseline_message_24);
            holder.bttn_message.setText("Message");
            holder.bttn_message.setCompoundDrawablesWithIntrinsicBounds(img, null,null,null);

            holder.bttn_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUserContactDialog(position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public Dialog showUserContactDialog(int position){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_usercontact);

        TextView tv_username = dialog.findViewById(R.id.tv_username);
        final TextView tv_phonenumber = dialog.findViewById(R.id.tv_phonenumber);
        ImageView imageView_userimage = dialog.findViewById(R.id.imageView_userimage);
        tv_username.setText(listings.get(position).getFirstname()+" "+listings.get(position).getMiddlename()+" "+listings.get(position).getLastname());
        tv_phonenumber.setText(listings.get(position).getPhonenumber());

        Picasso.get().load(imageURL+listings.get(position).getUserimage()).resize(37,37).into(imageView_userimage);

        final String phoneNumber = tv_phonenumber.getText().toString();

        tv_phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("phonenumber",phoneNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(context,"Number Copied.",Toast.LENGTH_LONG).show();
            }
        });


        dialog.show();
        return dialog;
    }


}
