package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.amplify.generated.graphql.CreateGfTastyNomsMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateGFTastyNomsInput;

public class AddNewTastyNomLocation extends AppCompatActivity {

    // Setup AWS functionality within this class
    private AWSAppSyncClient myAWSAppSyncClient;

    // Setup logging
    private static String TAG = "ss.AddNewTastyNoms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tasty_nom_location);

        Log.i(TAG, "in AddNewTastyNomLocation");

        // ============ More Setup To Communicate with AWS Within This Class ============
        myAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

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

        Button addNewBusinessData = findViewById(R.id.addBusinessDataButton);
        addNewBusinessData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TODO: Make text appear saying "Submitted!" on view.
                getBusinessName(v);
            }
        });
    } // <----- End of onCreate()

    // ============ Grab and Save Data User Wants to Save (from AddNewTastyNomLocation Activity) ============

    public void getBusinessName(View v) {
        // get business name and make into a usable string
        EditText businessNameEditText = findViewById(R.id.businessNameInput);
        String businessName = businessNameEditText.getText().toString();

        // get business address and make into a string
        EditText businessAddressEditText = findViewById(R.id.businessAddressInput);
        String businessAddress = businessAddressEditText.getText().toString();

        //TODO: Get info about GF menu - boolean
        //TODO: Get info about Dedicated GF - boolean
        //TODO: Get range for GF friendliness - int

        // Mutation is the name of anything you run that changes the stuff in your DB in anyway.
        // This is a builder pattern - to make an input, we create a builder and then call methods
        // for each individual thing we want to set up in the builder, and then call build at the end.

        // make the input
        CreateGFTastyNomsInput businessInput = CreateGFTastyNomsInput.builder()
                .nomplacename(businessName)
                .address(businessAddress)
                .build();

        // use a mutation to add data to DB
        myAWSAppSyncClient.mutate(CreateGfTastyNomsMutation.builder().input(businessInput).build())
                .enqueue(new GraphQLCall.Callback<CreateGfTastyNomsMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateGfTastyNomsMutation.Data> response) {
                        Log.i(TAG, response.data().toString());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.w(TAG, "failure");
                    }
                }
        );



    }
}
