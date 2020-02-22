package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content of main activity to be whatever exists in xml file
        setContentView(R.layout.activity_main);
    }
}
