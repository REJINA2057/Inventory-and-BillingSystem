package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDelete extends AppCompatActivity {
    Button deleteBtn;
    EditText productName,deleteCategory,supplier, date;

    ProgressDialog progressDialog;
    String drefp,product,cat, suppliername;
    String drefc;
    String drefs;
    DatabaseReference databaseReference;
    String category,quantitydelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        deleteBtn= findViewById(R.id.delete_button);
        productName= findViewById(R.id.delete_product_name);
        deleteCategory=findViewById(R.id.edit_delete_category);
        supplier= findViewById(R.id.add_supplier_delete);
        date= findViewById(R.id.add_date);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Items").child(productName.getText().toString());



        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String keyAsString = childSnapshot.getKey().toString();
                                System.out.println("Value of key of supplier"+keyAsString);
                                product= productName.getText().toString();

                                if(keyAsString.equals(product)){
                                    System.out.println("inside if\n");
                                    drefp=keyAsString;
                                    System.out.println("Value of drefp"+drefp);
                                    databaseReference.child(drefp).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                                    String keyAsString = childSnapshot.getKey();
                                                    System.out.println("Value of date" + keyAsString);
                                                    System.out.println("Value in text textfield"+ date.getText().toString());

                                                    if (keyAsString.compareTo(date.getText().toString())== 0) {
                                                        databaseReference.child(keyAsString).removeValue();
                                                        break;
                                                    }
                                                }
                                            }else{
                                                System.out.println("hello");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }

                                    });
                                            //System.out.println(product);

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void deleteItem(int value) {

    }

}