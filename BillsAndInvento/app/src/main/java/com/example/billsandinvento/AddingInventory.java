package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;


public class AddingInventory extends AppCompatActivity{


    EditText productName,supplierName,phoneNumber,itemQty,category,price;
    Button enterItem, cancelButton;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    private DatabaseReference rootDatabasereference;

    String drefp,product,cat, supplier;
    String drefc;
    String drefs;
    DatabaseReference dref;
    int available;
    long item_value=0;

//    private FirebaseAuth firebaseAuth;
//    DatabaseReference databaseReference;
//    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        getSupportActionBar().hide();

        productName= findViewById(R.id.add_product_name);
        supplierName = findViewById(R.id.add_supplier_name);
        phoneNumber = findViewById(R.id.add_supplier_phone);
        itemQty= findViewById(R.id.add_no_of_item);
        //availableQty= findViewById(R.id.add_available_item);
        category=findViewById(R.id.add_category);
        price=findViewById(R.id.add_price_of_item);

        progressDialog= new ProgressDialog(this);

        enterItem= findViewById(R.id.add_button);
        cancelButton= findViewById(R.id.add_cancel_button);

        System.out.println(product);

        enterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemValueInitialization();
                nextUserActivity();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddingInventory.this, DashBoard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        });
    }

    private void itemValueInitialization() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Items");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyAsString = childSnapshot.getKey().toString();
                        product = productName.getText().toString();
                        if (keyAsString.equals(product)){
                            databaseReference.child(keyAsString).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            System.out.println(snapshot.getChildrenCount());
                                            item_value=childSnapshot.getChildrenCount();
                                            addingItem(item_value);
                                        }else{
                                            addingItem(item_value);
                                        }
                                    }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        }else{
                            addingItem(item_value);
                        }
                    }
                }else{
                    addingItem(item_value);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private void addingItem(long item_value) {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Items");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(new Date());

        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("itemQty", itemQty.getText().toString());
        m.put("productName",productName.getText().toString());
        m.put("costPrice", price.getText().toString());
        m.put("supplierPhone", phoneNumber.getText().toString());
        m.put("supplierName",supplierName.getText().toString());
        m.put("purchaseDate",dateString);
        m.put("category",category.getText().toString());
        long item=item_value+1;
        m.put("productId",item);
        databaseReference.child(productName.getText().toString()).child(String.valueOf(item)).setValue(m);
        itemAvailable(category.getText().toString(),productName.getText().toString());
        nextUserActivity();
    }

    private void itemAvailable(String productCategory, String itemName) {
        databaseReferencecat=FirebaseDatabase.getInstance().getReference().child("Available");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyAsString = childSnapshot.getKey().toString();
                        //String product = productName.getText().toString();
                        if (keyAsString.equals(itemName)){
                            databaseReference.child(keyAsString).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                            System.out.println(childSnapshot.child("category").getValue().toString());

                                            if(childSnapshot.child("category").getValue().toString().equals(productCategory)){
                                                int availableitem= Integer.parseInt(childSnapshot.child("itemQty").getValue().toString());
                                                available+=availableitem;
                                                System.out.println(available);
                                                HashMap<String, Object> n= new HashMap<String, Object>();
                                                n.put("availableQty",String.valueOf(available));
                                                n.put("productCategory",productCategory);
                                                n.put("productName",itemName);
                                                databaseReferencecat.child(itemName).child(productCategory).setValue(n);
                                            }

                                        }
                                        available=0;
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    private void nextUserActivity() {
        Toast.makeText(AddingInventory.this, "Item Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddingInventory.this, DashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}