package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amazonaws.amplify.generated.graphql.CreateGfTastyNomsMutation;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

        // ============ AWS S3 Service Setup - to start TransferUtility ============
        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        // ============ AWS MobileClient - for setting up S3 Service ============
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(TAG, "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());

                uploadWithTransferUtility();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Initialization error.", e);
            }
        });

        // ============ Buttons that Go Places ============
        Button goToAllLocations = findViewById(R.id.goToAllNomPlacesFromAddNewPlace);
        goToAllLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllLocationsWithIntent = new Intent(
                        AddNewTastyNomLocation.this, AllTastyNoms.class);
                AddNewTastyNomLocation.this.startActivity(goToAllLocationsWithIntent);
            }
        });

        Button goToMainActivity = findViewById(R.id.goToMainActivityFromAddNewLocation);
        goToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivityWithIntent = new Intent(
                        AddNewTastyNomLocation.this, MainActivity.class);
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
    } // <--------------- End of onCreate()


    // ============ Grab & Save Data that the User Wants to Add to AWS' DynamoDB ============

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
                    public void onResponse(
                            @Nonnull Response<CreateGfTastyNomsMutation.Data> response) {
                        Log.i(TAG, response.data().toString());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.w(TAG, "failure in adding data to dynamoDB");
                    }
                }
        );
    }


    // ============ Select Image From Phone ============

    // --- Select Image from Phone ---
    // starting code from viralpatel.net/pick-image-from-galaxy-android-app/

    public void pickImage(View v) {
        // Test button - in xml file, onClick in Common Attributes is set to "pickImage"
        Log.i(TAG, "The Select-Image button clicked!");

        // trigger an intent to go pick image in the phone and get URI
        Intent intent = new Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // requestCode is created by developer
        startActivityForResult(intent, 777);
    }

    // --- Handle Data the User Picked from the Phone ---

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // when activity finishes, the requestCode, resultCode and data will be supplied to the developer
        // requestCode allows us to check if code is 777 or something else.

        if (requestCode == 777 && resultCode == RESULT_OK && null != data) {
            // get the URI for the image
            Uri selectedImage = data.getData();

            // grab the info where we should put the selected image from the xml file
            ImageView imageView = findViewById(R.id.imageView);

            // change the image currently on the xml view and replace with URI of new image
            imageView.setImageURI(selectedImage);
        }
    }


    // ============ Uses Transfer Utility to Upload something to S3 ============
    // from: https://aws-amplify.github.io/docs/sdk/android/storage

    public void uploadWithTransferUtility() {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        File file = new File(getApplicationContext().getFilesDir(), "sample.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("Howdy World!");
            writer.close();
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/sample.txt",
                        new File(getApplicationContext().getFilesDir(),"sample.txt"));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d(TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d(TAG, "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d(TAG, "Bytes Total: " + uploadObserver.getBytesTotal());
    }
}
