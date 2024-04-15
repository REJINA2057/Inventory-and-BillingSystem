package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Bill extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database, databaseReferencecat;
    BillsAdapter billsAdapter;
    ArrayList<BillsDetails> avalist;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bill);
            getSupportActionBar().hide();

            //enterItem= findViewById(R.id.newItem);
            recyclerView = findViewById(R.id.sellingitem);
            database = FirebaseDatabase.getInstance().getReference("Customers");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            avalist = new ArrayList<>();
            billsAdapter = new BillsAdapter(this,avalist);
            recyclerView.setAdapter(billsAdapter);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = dateFormat.format(new Date());
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String customer = dataSnapshot.getKey();
                        System.out.println(customer);
                        database.child(customer).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                        if(dataSnapshot.child("soldDate").toString().equals(dateString)){
                                        System.out.println(dataSnapshot);
                                        BillsDetails bd = dataSnapshot.getValue(BillsDetails.class);
                                        avalist.add(bd);
//
                                    }
                                    billsAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //show.setEnabled(false);



//        //enterItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Bill.this, SalesInfo.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//            }
//
//        });
    }

}