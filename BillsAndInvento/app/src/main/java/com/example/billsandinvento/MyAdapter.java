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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<ProductDetails> list;




    public MyAdapter(Context context, ArrayList<ProductDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductDetails pd = list.get(position);
        System.out.println(list.get(position));
        holder.productId.setText(Long.toString(pd.getProductId()));
        holder.purchaseDate.setText(pd.getPurchaseDate());
        holder.productName.setText(pd.getProductName());
        holder.category.setText(pd.getCategory());
        holder.itemQty.setText(pd.getItemQty());
        holder.supplierName.setText(pd.getSupplierName());
        holder.supplierPhone.setText(pd.getSupplierPhone());
        holder.costPrice.setText(pd.getCostPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView category, purchaseDate,costPrice,itemQty,supplierPhone,supplierName,productName,productId;
        ImageView imageView;

        DatabaseReference database;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.tvcategory);
            purchaseDate = itemView.findViewById(R.id.tvpurchaseDate);
            costPrice = itemView.findViewById(R.id.tvcostPrice);
            itemQty= itemView.findViewById(R.id.tvitemQty);
            supplierPhone = itemView.findViewById(R.id.tvsupplierPhone);
            supplierName= itemView.findViewById(R.id.tvsupplierName);
            productName= itemView.findViewById(R.id.tvproductName);
            imageView = itemView.findViewById(R.id.delete_button);
            productId = itemView.findViewById(R.id.tvproductId);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database = FirebaseDatabase.getInstance().getReference("Items").child(productName.getText().toString()).child(productId.getText().toString());
                    database.removeValue();
                    Toast.makeText(itemView.getContext(), "Item Deleted from Database", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), DashBoard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

}
