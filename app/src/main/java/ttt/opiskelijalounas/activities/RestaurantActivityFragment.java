package ttt.opiskelijalounas.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ttt.opiskelijalounas.R;
import ttt.opiskelijalounas.models.Restaurant;
import ttt.opiskelijalounas.utils.MyAdapter;


public class RestaurantActivityFragment extends Fragment implements MyAdapter.IViewHolderListener {

    private OnFragmentInteractionListener mListener;

    public RestaurantActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);

        ArrayList<Restaurant> restaurants = new ArrayList<>();

        // Aukioloaikoja ei enää näytetä 6.4, pitäisi hakea netistä eikä kovakoodata tähän...
        restaurants.add(new Restaurant("Bittipannu", "Dynamo", "Ma-Pe 11:30 - 17:00", "5865"));
        restaurants.add(new Restaurant("Radis", "Pääkampus", "Ma-Pe 10:30 - 14:00", "5859"));
        restaurants.add(new Restaurant("Fuuga", "Musiikkikampus", "Ma-Pe 10:30 - 14:00", "5868"));
        restaurants.add(new Restaurant("Rajacafé", "Pääkampus", "Ma-Pe 10:30 - 13:30", "5861"));

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter(restaurants, this));

        return v;
    }

    @Override
    public void onClick(String sodexoId, String restaurant) {
        mListener.onFragmentInteraction(sodexoId, restaurant);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String sodexoId, String restaurant);
    }
}
