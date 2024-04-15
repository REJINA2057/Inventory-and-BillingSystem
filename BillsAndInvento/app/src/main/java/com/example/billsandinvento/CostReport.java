package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CostReport extends AppCompatActivity {

    BarChart barChart;

    DatabaseReference databaseReference, databaseReferenceroot;

    int  year;

    String dateString;

    Date date;

    int actual=0,key=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_report);

        getSupportActionBar().hide();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
//        databaseReferenceroot = FirebaseDatabase.getInstance().getReference().child("Annual Sales");
        System.out.println("total sales value");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Integer> values = new ArrayList<>();
                    List<Integer> keys = new ArrayList<>();
                    List<Integer> actualValues= new ArrayList<>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String keyAsString = childSnapshot.getKey().toString();
                        System.out.println("I am here" + keyAsString);
                        databaseReference.child(keyAsString).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                        String child = childSnapshot.getKey().toString();
                                        System.out.println("i am there" + child);
                                        databaseReference.child(keyAsString).child(child).addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    System.out.println("i am inside " + child);
                                                    dateString = snapshot.child("purchaseDate").getValue().toString();
                                                    try {
                                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                        date = format.parse(dateString);
                                                        Calendar calendar = Calendar.getInstance();
                                                        calendar.setTime(date);
                                                        year = calendar.get(Calendar.YEAR);

                                                        actual=key;
                                                        actualValues.add(actual);
//

                                                        key = year;
//                                                        System.out.println("value of key"+key);
//                                                         for(int i =0; i<actualValues.size();i++){
//                                                             System.out.println("value of actual"+actualValues.get(i));
//                                                             if(actualValues.get(i).equals(key)){
//                                                                 value+= snapshot.child("totalPrice").getValue(Integer.class);
//                                                             }else{
//                                                                 System.out.println("value of actual inside else"+actualValues.get(i));
//                                                                 value= snapshot.child("totalPrice").getValue(Integer.class);
//                                                             }
//                                                         }
                                                        keys.add(key);

                                                        int value = Integer.parseInt(snapshot.child("costPrice").getValue(String.class)) ;
                                                        values.add(value);
                                                        System.out.println(value);
                                                        System.out.println(key);
                                                        reportGenerating(actualValues,keys,values);
                                                        // Use the date object as needed
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }

                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
//                    information = new ArrayList<>();
//                    information.add(new BarEntry(newdate,totalSales));
//                    BarDataSet dataSet = new BarDataSet(information,"Sales Report");
//                    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//                    dataSet.setValueTextColor(Color.BLACK);
//                    dataSet.setValueTextSize(20f);
//
//                    BarData barData = new BarData(dataSet);
//                    barChart.setFitBars(true);
//                    barChart.setData(barData);
//                    barChart.getDescription().setText("Bar Report");
//                    barChart.animateY(2000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private void reportGenerating(List<Integer> actualValues, List<Integer> keys, List<Integer> values) {
        List<BarEntry> entries = new ArrayList<>();
        //List<BarEntry> entryValues = new ArrayList<>();
        barChart = (BarChart) findViewById(R.id.costbarchart);
        for (int i = 0; i < values.size(); i++) {
//            if (values.size() == 1) {
//                entries.add(new BarEntry(keys.get(i), values.get(i)));
//            } else {
//                actual = values.get(i);
//                    if (actualValues.get(i).equals(keys.get(i))) {
//                        actual += values.get(i);
//                    }
//            }
            entries.add(new BarEntry(keys.get(i),values.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Bar Chart");
        System.out.println(entries);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        //dataSet.setDrawValues(false);
        dataSet.setBarBorderWidth(0f); // Remove bar border
        dataSet.setValueTextSize(12f);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);
        barData.setBarWidth(0.9f);
        //barData.setDrawValues(false);

        //barChart.groupBars(0f, 0.1f, 0.02f);
        // Adjust bar width (0.9f is the default value)
        barChart.invalidate();
    }
}