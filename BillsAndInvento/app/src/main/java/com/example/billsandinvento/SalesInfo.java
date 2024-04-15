package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import kotlinx.coroutines.flow.internal.SafeCollectorKt;

public class SalesInfo extends AppCompatActivity {
    EditText productName,customerName,phoneNumber,itemQty,category,price;
    Button enterItem, cancelButton,billButton;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat,rootDatabasereference;
    //private DatabaseReference rootDatabasereference;

    String drefp,product,cat, supplier,customer,soldqty;
    String drefc;
    String drefs;
    DatabaseReference dref;
    int year;
    int dateValue=0;

    int soldValue;
    long item_id=0, sno=0;


    Date date;


//    private FirebaseAuth firebaseAuth;
//    DatabaseReference databaseReference;
//    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_info);
        getSupportActionBar().hide();

        productName= findViewById(R.id.billProduct);
        customerName = findViewById(R.id.add_customer_name);
        phoneNumber = findViewById(R.id.customer_phone);
        itemQty= findViewById(R.id.billItem);
        //availableQty= findViewById(R.id.add_available_item);
        category=findViewById(R.id.billCategory);
        price=findViewById(R.id.per_item_amount);

        progressDialog= new ProgressDialog(this);

        enterItem= findViewById(R.id.item_add_button);
        cancelButton= findViewById(R.id.return_button);
        billButton= findViewById(R.id.billView);

        enterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSold();

            }
        });
        billButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesInfo.this, Bill.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesInfo.this, DashBoard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        });
    }
    private void itemValueInitialization() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Customers");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyAsString = childSnapshot.getKey().toString();
                        customer= customerName.getText().toString();
                        if (keyAsString.equals(customer)){
                            databaseReference.child(keyAsString).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        System.out.println("children count:" + childSnapshot.getChildrenCount());
                                        item_id = childSnapshot.getChildrenCount();
                                        addingItem(item_id);

                                    } else {
                                        addingItem(item_id);
                                    }
                                }
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        }else{
                            addingItem(item_id);
                        }
                    }
                }else{
                    addingItem(item_id);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void itemSold() {
        databaseReferencecat=FirebaseDatabase.getInstance().getReference().child("Available");
        databaseReferencecat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyAsString = childSnapshot.getKey().toString();
                        System.out.println("KeyString value"+keyAsString);
                        product=productName.getText().toString();
                        System.out.println("product value"+product);
                        if (keyAsString.equals(product)){
                            databaseReferencecat.child(keyAsString).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                            String key = childSnapshot.getKey().toString();
                                            System.out.println("Key value"+key);
                                            cat=category.getText().toString();
                                            if(key.equals(cat)){
                                                databaseReferencecat.child(keyAsString).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            soldqty=itemQty.getText().toString();
                                                            int deducted=Integer.parseInt(snapshot.child("availableQty").getValue().toString());
                                                            if(deducted<Integer.parseInt(soldqty)){
                                                                Toast.makeText(SalesInfo.this, "Item Unavailable", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                itemValueInitialization();
                                                                String value= String.valueOf(deducted-Integer.parseInt(soldqty));
                                                                databaseReferencecat.child(keyAsString).child(key).child("availableQty").setValue(value);
                                                                Toast.makeText(SalesInfo.this, "Item Deducted", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                break;
                                            }
                                        }
                                    }else{
                                        Toast.makeText(SalesInfo.this, "No Item Available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        }else{
                            System.out.println("Your here");
                        }
                        //break;
                    }
                }else{
                    Toast.makeText(SalesInfo.this, "No product available", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    private void addingItem(long item_id) {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Customers");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(new Date());

        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("soldQty", itemQty.getText().toString());
        m.put("productName",productName.getText().toString());
        long total=Integer.parseInt(price.getText().toString()) * Integer.parseInt(itemQty.getText().toString()) ;
        m.put("perItem",price.getText().toString());
        m.put("totalPrice",total);
        m.put("customerPhone", phoneNumber.getText().toString());
        m.put("customerName",customerName.getText().toString());
        m.put("soldDate",dateString);
        m.put("category",category.getText().toString());
        long itemid=item_id+1;
        m.put("productId",itemid);
        databaseReference.child(customerName.getText().toString()).child(String.valueOf(itemid)).setValue(m);
        nextUserActivity();
        //itemAdded(customerName.getText().toString(),category.getText().toString(),productName.getText().toString());
    }
    private void nextUserActivity() {
        Toast.makeText(SalesInfo.this, "Item Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SalesInfo.this,SalesInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
