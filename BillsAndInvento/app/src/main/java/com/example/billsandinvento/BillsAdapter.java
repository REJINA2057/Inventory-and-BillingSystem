package com.example.billsandinvento;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.MyViewHolder> {
    Context cont;

    ArrayList<BillsDetails> avalist;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cont).inflate(R.layout.billlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BillsDetails bd = avalist.get(position);
        System.out.println(avalist.get(position));
        holder.productId.setText(Long.toString(bd.getProductId()));
        holder.customerName.setText(bd.getCustomerName());
        holder.customerPhone.setText(bd.getCustomerPhone());
        holder.productName.setText(bd.getProductName());
        holder.category.setText(bd.getCategory());
        holder.soldQty.setText(bd.getSoldQty());
        holder.peritem.setText(bd.getPerItem());
        holder.totalprice.setText(Long.toString(bd.getTotalPrice()));
        holder.soldDate.setText(bd.getSoldDate());
    }

    @Override
    public int getItemCount() {
        return avalist.size();
    }


    public BillsAdapter(Context cont, ArrayList<BillsDetails> avalist) {
        this.cont = cont;
        this.avalist = avalist;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView category,soldQty,productName,productId,totalprice,peritem,soldDate,customerName, customerPhone;
        //DatabaseReference customerdatabase;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.bill_catrgory);
            productName = itemView.findViewById(R.id.bill_product_name);
            soldQty= itemView.findViewById(R.id.bill_quantity);
            totalprice = itemView.findViewById(R.id.bill_total_pricee);
            soldDate= itemView.findViewById(R.id.bill_date);
            peritem= itemView.findViewById(R.id.bill_per_price);
            customerName= itemView.findViewById(R.id.bill_customer_name);
            customerPhone= itemView.findViewById(R.id.bill_customer_phone);
            productId= itemView.findViewById(R.id.soldproductId);

        }
    }
}
