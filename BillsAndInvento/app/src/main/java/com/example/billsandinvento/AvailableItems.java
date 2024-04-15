package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableItems extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    AvailableAdapter availableAdapter;
    ArrayList<AvailableItemDetails> avalist;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_available_items);
            getSupportActionBar().hide();

            recyclerView = findViewById(R.id.availableitemdetails);
            database = FirebaseDatabase.getInstance().getReference("Available");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            avalist = new ArrayList<>();
            availableAdapter = new AvailableAdapter(this,avalist);
            recyclerView.setAdapter(availableAdapter);
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String keyAsString = dataSnapshot.getKey();
                        database.child(keyAsString).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot childSnapshot : snapshot.getChildren()){
                                        AvailableItemDetails pd = childSnapshot.getValue(AvailableItemDetails.class);
                                        avalist.add(pd);
                                    }
                                    availableAdapter.notifyDataSetChanged();
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
        }
}