package com.example.sriram.weatherapp;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Cities");

        MainActivity.tv.setText("cities"+mDatabase.getParent().toString());
        Toast.makeText(getApplicationContext(),mDatabase.toString(),Toast.LENGTH_SHORT);
    }
}
