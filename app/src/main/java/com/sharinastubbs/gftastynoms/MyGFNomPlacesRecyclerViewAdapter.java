package com.sharinastubbs.gftastynoms;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharinastubbs.gftastynoms.GFNomPlacesFragment.OnListFragmentInteractionListener;
import com.sharinastubbs.gftastynoms.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGFNomPlacesRecyclerViewAdapter extends RecyclerView.Adapter<MyGFNomPlacesRecyclerViewAdapter.ViewHolder> {

    private static String TAG = "ss.RecyclerViewAdapter";

    private final List<GFTastyNomPlace> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGFNomPlacesRecyclerViewAdapter(List<GFTastyNomPlace> items, OnListFragmentInteractionListener listener) {
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
        holder.mNomPlaceNameView.setText(mValues.get(position).getNomplacename());
        holder.mAddressView.setText(mValues.get(position).getAddress());

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
        public final View mView;
        public final TextView mNomPlaceNameView;
        public final TextView mAddressView;
        public GFTastyNomPlace mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNomPlaceNameView = (TextView) view.findViewById(R.id.placeName);
            mAddressView = (TextView) view.findViewById(R.id.placeAddress);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAddressView.getText() + "'";
        }
    }
}
