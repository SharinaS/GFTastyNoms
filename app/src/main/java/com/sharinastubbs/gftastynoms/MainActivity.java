package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Note on logging: verbose, debug, info are the lower level logs; warning, error, wtf are the
    // highest levels of urgency. Different levels allows programmer to leave these logs in the app
    // without littering the app.
    private static String TAG = "ss.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content of main activity to be whatever exists in xml file
        setContentView(R.layout.activity_main);

        Log.d(TAG, "in onCreate");

        // ============ Buttons To Go Places ============
        // tell it where to go when the button is clicked... and secretly define a class (anonymous
        // inner class) that implements the onClickListener interface.

        Button goToAllTastyNomsView = findViewById(R.id.goToAllNoms);
        goToAllTastyNomsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTastyNomsViewWithIntent = new Intent(MainActivity.this, AllTastyNoms.class);
                MainActivity.this.startActivity(goToAllTastyNomsViewWithIntent);
            }
        });

        Button goToAddNewLocationView = findViewById(R.id.goToAddNewNomLocation);
        goToAddNewLocationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddNewLocationViewWithIntent = new Intent(MainActivity.this, AddNewTastyNomLocation.class);
                MainActivity.this.startActivity(goToAddNewLocationViewWithIntent);
            }
        });
    }
}
