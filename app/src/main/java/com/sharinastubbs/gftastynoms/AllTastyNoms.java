package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllTastyNoms extends AppCompatActivity {

    private static String TAG = "ss.AllTastyNoms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasty_noms);

        // ============ Buttons To Go Places ============
        Button goToAddNewNomPlace = findViewById(R.id.goToAddNewLocationFromAllTastyNoms);
        goToAddNewNomPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddNewNomLocationWithIntent = new Intent(AllTastyNoms.this, AddNewTastyNomLocation.class);
                AllTastyNoms.this.startActivity(goToAddNewNomLocationWithIntent);
            }
        });

        Button goToMainActivity = findViewById(R.id.goToMainActivityFromAllTastyNoms);
        goToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivityWithIntent = new Intent(AllTastyNoms.this, MainActivity.class);
                AllTastyNoms.this.startActivity(goToMainActivityWithIntent);
            }
        });
    }

    //TODO: add recycler view to this page to show all the locations available.

}
