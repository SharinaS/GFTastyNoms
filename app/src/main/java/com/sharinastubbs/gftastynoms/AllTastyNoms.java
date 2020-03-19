package com.sharinastubbs.gftastynoms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.amazonaws.amplify.generated.graphql.ListGfTastyNomssQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

public class AllTastyNoms extends AppCompatActivity {

    private static String TAG = "ss.AllTastyNoms";

    // Setup AWS functionality within this class
    private AWSAppSyncClient myAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasty_noms);

        // ============ More Setup To Communicate with AWS Within This Class ============
        myAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

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
    } // <------- end onCreate()


    // ========== Get Data From DynamoDB ==========
    // starting code from aws-amplify.github.io/docs/sdk/android/api
    public void getAllTheTastyNomDataFromDynamoDB() {
        myAWSAppSyncClient.query(ListGfTastyNomssQuery.builder().build())
                // will cache data on device automatically
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<ListGfTastyNomssQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<ListGfTastyNomssQuery.Data> response) {
                        List<ListGfTastyNomssQuery.Item> responseItems = response.data().listGFTastyNomss().items();

                        for (ListGfTastyNomssQuery.Item item : responseItems) {
                            String TastyNomPlaceName = item.nomplacename();
                            // TODO: Make a class for TastyNomPlaces so a list can collect the TastyNomPlaces....
                        }
                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message inputMessage) {
                                // grab data out of Message object and render it to the page via recyler view.
                                //TODO: finish this up (https://frontrowviews.com/Home/Event/Play/5d76a96ebdb9b10d0cb5ce1c)
                            }
                        };
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {

                    }
                });
    }

}
