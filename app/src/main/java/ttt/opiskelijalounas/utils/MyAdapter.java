package ttt.opiskelijalounas.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ttt.opiskelijalounas.R;
import ttt.opiskelijalounas.models.Restaurant;

/**
 * Created by Teemu on 16.12.2016.
 *
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final ArrayList<Restaurant> restaurants;
    private final IViewHolderListener mListener;

    public MyAdapter(ArrayList<Restaurant> restaurants, IViewHolderListener listener) {
        this.restaurants = restaurants;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_layout, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.restaurantTitle.setText(restaurant.getTitle());
        holder.restaurantSubtitle.setText(restaurant.getSubtitle());
//        holder.restaurantServingTimes.setText(restaurant.getServingTimes());
        holder.sodexoId = restaurants.get(position).getSodexoID();
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public interface IViewHolderListener {
        void onClick(String sodexoId, String restaurant);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView restaurantTitle;
        final TextView restaurantSubtitle;
//        final TextView restaurantServingTimes;
        String sodexoId;

        ViewHolder(View itemView, IViewHolderListener listener) {
            super(itemView);
            mListener = listener;
            restaurantTitle = (TextView) itemView.findViewById(R.id.restaurantTitle);
            restaurantSubtitle = (TextView) itemView.findViewById(R.id.restaurantSubtitle);
//            restaurantServingTimes = (TextView) itemView.findViewById(R.id.restaurantServingTimes);
            itemView.setOnClickListener(this);
        }

        final IViewHolderListener mListener;

        @Override
        public void onClick(View view) {
            mListener.onClick(sodexoId, restaurantTitle.getText().toString());
        }
    }
}
