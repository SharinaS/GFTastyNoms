package com.sharinastubbs.gftastynoms;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.amplify.generated.graphql.GetGfTastyNomsQuery;
import com.amazonaws.amplify.generated.graphql.ListGfTastyNomssQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GFNomPlacesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    // instance variable for recycler view
    private RecyclerView recyclerView;

    // Setup AWS functionality within this class
    private AWSAppSyncClient myAWSAppSyncClient;

    // Setup logging
    private static String TAG = "ss.GFNomPlacesFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GFNomPlacesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GFNomPlacesFragment newInstance(int columnCount) {
        GFNomPlacesFragment fragment = new GFNomPlacesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gfnomplaces_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

//            // make a list to hold the different GF locations
//            List<GFTastyNomPlace> listOfGFTastyNomPlaces = new LinkedList<>();
//            // add a location
//            listOfGFTastyNomPlaces.add(new GFTastyNomPlace(
//                    "Flying Apron",
//                    "4709 California Ave SW, Seattle, WA 98116",
//                    true,
//                    true,
//                    10));
//
//            listOfGFTastyNomPlaces.add(new GFTastyNomPlace(
//                    "Matador",
//                    "4546 California Ave SW, Seattle, WA 98116",
//                    false,
//                    false,
//                    6));
//
//            // populate the recycler view
//            recyclerView.setAdapter(new MyGFNomPlacesRecyclerViewAdapter(listOfGFTastyNomPlaces, null));
        }

        // ============ More Setup To Communicate with AWS Within This Class ============
        myAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(view.getContext().getApplicationContext())
                .awsConfiguration(new AWSConfiguration(view.getContext().getApplicationContext()))
                .build();
        return view;
    }

    // ====== Access Data in DynamoDB to be Displayed in the Recycler View ======
    // Note that CACHE_AND_NETWORK don't always get the latest updates, so we just do NETWORK_FIRST.
    @Override
    public void onResume() {
        super.onResume();
        myAWSAppSyncClient.query(ListGfTastyNomssQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.NETWORK_FIRST)
                .enqueue(new GraphQLCall.Callback<ListGfTastyNomssQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull final Response<ListGfTastyNomssQuery.Data> response) {

                        // Address threading issues, given callback isn't running on the UI thread
                        Handler handler = new Handler(Looper.getMainLooper()){
                            //define how the handler is supposed to work
                            @Override
                            public void handleMessage(Message inputMessage) {
                                recyclerView.setAdapter(new MyGFNomPlacesRecyclerViewAdapter(response.data().listGFTastyNomss().items(), null));
                            }
                        };
                        handler.obtainMessage().sendToTarget();
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.i(TAG, e.getMessage());
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
    }
}
