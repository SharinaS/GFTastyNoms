package com.sharinastubbs.gftastynoms;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListGfTastyNomssQuery;
import com.sharinastubbs.gftastynoms.GFNomPlacesFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyGFNomPlacesRecyclerViewAdapter extends RecyclerView.Adapter<MyGFNomPlacesRecyclerViewAdapter.ViewHolder> {

    private static String TAG = "ss.RecyclerViewAdapter";

    private final List<ListGfTastyNomssQuery.Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGFNomPlacesRecyclerViewAdapter(List<ListGfTastyNomssQuery.Item> items,
                                            OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    // creates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_gfnomplaces, parent, false);
        return new ViewHolder(view);
    }

    // Given the holder and the position index, fill in that view with the right data for that position.
    // This is where the data fills into the row so that data can be displayed.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // use getters from the GFNomPlace class. Note that if you use setText, you have to pass in a String.
        // If you have an int to pass in, you can do: ("" + mValues.get(position)....).
        holder.mNomPlaceNameView.setText(mValues.get(position).nomplacename());
        holder.mAddressView.setText(mValues.get(position).address());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "it was clicked!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Instance variables for the data that shows up on the recycler view to hold the reference to that individual text view.
        public final View mView;
        public ListGfTastyNomssQuery.Item mItem;
        // items that appear on the recycler view
        public final TextView mNomPlaceNameView;
        public final TextView mAddressView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            // grab ids of text TextViews
            mNomPlaceNameView = (TextView) view.findViewById(R.id.placeName);
            mAddressView = (TextView) view.findViewById(R.id.placeAddress);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAddressView.getText() + "'";
        }
    }
}
