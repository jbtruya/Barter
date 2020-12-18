package com.example.barter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    Context context;
    ArrayList<OfferObject> offersArrayList;
    User user;

    ArrayList<TransactionObject> transactionObjectArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView tv_transactionid;
        TextView tv_listingid;
        TextView tv_transactiondate;
        TextView tv_productname1;
        TextView tv_productname2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_transactionid = itemView.findViewById(R.id.tv_transactionid);
            tv_listingid = itemView.findViewById(R.id.tv_listingid);
            tv_transactiondate = itemView.findViewById(R.id.tv_transactiondate);
            tv_productname1 = itemView.findViewById(R.id.tv_productName1);
            tv_productname2 = itemView.findViewById(R.id.tv_productName2);
        }
    }

    TransactionAdapter(Context context,ArrayList<TransactionObject> transactionObjectArrayList){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.transactionObjectArrayList = transactionObjectArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_transactions,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.tv_transactionid.setText("Transction id:"+String.valueOf(transactionObjectArrayList.get(position).getTransactionid()));
            holder.tv_listingid.setText("Listing id:"+String.valueOf(transactionObjectArrayList.get(position).getListingid()));
            holder.tv_transactiondate.setText(transactionObjectArrayList.get(position).getTransactionDate().replace(" ", "\n"));
            holder.tv_productname1.setText("Your Listing:"+transactionObjectArrayList.get(position).getProductname());
            holder.tv_productname2.setText("Offered:"+transactionObjectArrayList.get(position).getProductname2());


    }

    @Override
    public int getItemCount() {
        return transactionObjectArrayList.size();
    }
}
