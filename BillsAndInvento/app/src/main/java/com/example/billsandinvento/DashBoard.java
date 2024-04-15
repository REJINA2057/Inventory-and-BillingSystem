package com.example.billsandinvento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
//import android.hardware.biometrics.BiometricPrompt;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DashBoard extends AppCompatActivity{
    ImageView logOut,addItem,availableItems,goodsBought,billDetail,yearlyreport,regbtn;

    //public static final String SHARED_PREFS="sharedPrefs";

    //BiometricPrompt biometricPrompt;
//    BiometricPrompt.PromptInfo promptInfo;=

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey)));
        getSupportActionBar().hide();
        addItem=findViewById(R.id.additem);
        availableItems=findViewById(R.id.availableDetails);
        goodsBought=findViewById(R.id.goodsboughtdetails);
        billDetail=findViewById(R.id.bill);
        yearlyreport=findViewById(R.id.yearlyreport);
        regbtn=findViewById(R.id.buttonreg);
        logOut=findViewById(R.id.logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, Login.class);
                startActivity(intent);
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, AddingInventory.class);
                startActivity(intent);
            }
        });

        availableItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, AvailableItems.class);
                startActivity(intent);
            }
        });
        goodsBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, Itemlist.class);
                startActivity(intent);
            }
        });
        billDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, SalesInfo.class);
                startActivity(intent);
            }
        });
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, RegisterUser.class);
                startActivity(intent);
            }
        });

        yearlyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this,YearlyReport.class);
                startActivity(intent);
            }
        });

    }
}