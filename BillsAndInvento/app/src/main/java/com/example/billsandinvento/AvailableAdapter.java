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
public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.MyViewHolder> {
        Context cont;

        ArrayList<AvailableItemDetails> avalist;


        public AvailableAdapter(Context cont, ArrayList<AvailableItemDetails> avalist) {
            this.cont = cont;
            this.avalist = avalist;
        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cont).inflate(R.layout.availableitemlist, parent, false);
        return new MyViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AvailableItemDetails ad = avalist.get(position);
        System.out.println(avalist.get(position));
        holder.productName.setText(ad.getProductName());
        holder.productCategory.setText(ad.getProductCategory());
        holder.availableQty.setText(ad.getAvailableQty());

    }

    @Override
    public int getItemCount() {
        return avalist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productCategory,availableQty,productName;
        ImageView imageViewavailable;

        DatabaseReference availabedatabase;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productCategory = itemView.findViewById(R.id.availablecategory);
            productName = itemView.findViewById(R.id.availableproductName);
            availableQty= itemView.findViewById(R.id.availableQty);
            imageViewavailable= itemView.findViewById(R.id.available_delete);

            imageViewavailable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    availabedatabase = FirebaseDatabase.getInstance().getReference("Available").child(productName.getText().toString()).child(productCategory.getText().toString());
                    availabedatabase.removeValue();
                    Toast.makeText(itemView.getContext(), "Item Deleted from Database", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(),DashBoard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
