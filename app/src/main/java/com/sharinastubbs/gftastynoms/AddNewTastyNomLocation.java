package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddNewTastyNomLocation extends AppCompatActivity {

    private static String TAG = "ss.AddNewTastyNoms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tasty_nom_location);

        // ============ Buttons To Go Places ============
        Button goToAllLocations = findViewById(R.id.goToAllNomPlacesFromAddNewPlace);
        goToAllLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllLocationsWithIntent = new Intent(AddNewTastyNomLocation.this, AllTastyNoms.class);
                AddNewTastyNomLocation.this.startActivity(goToAllLocationsWithIntent);
            }
        });

        Button goToMainActivity = findViewById(R.id.goToMainActivityFromAddNewLocation);
        goToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivityWithIntent = new Intent(AddNewTastyNomLocation.this, MainActivity.class);
                AddNewTastyNomLocation.this.startActivity(goToMainActivityWithIntent);
            }
        });
    }
}
