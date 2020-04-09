package com.sanath.mileage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanath.mileage.R;
import com.sanath.mileage.model.MileageModel;

import java.util.List;

//Adapter class for RecyclerView containing all the mileages which are displayed on the MainActivity
public class MileageCardAdapter extends RecyclerView.Adapter<MileageCardAdapter.MyViewHolder> {

    private List<MileageModel> mileageData;
    private Context context;

    //Constructor to initialize the values to be displayed in the card
    public MileageCardAdapter(Context context, List<MileageModel> mileageData) {
        this.context = context;
        this.mileageData = mileageData;
    }

    @NonNull
    @Override
    //Inflated the individual cards
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mileage_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    //Binds data to the inflated cards
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MileageModel mileageModel = mileageData.get(position);
        holder.tvMileage.setText(String.valueOf(mileageModel.getMileage()) + " kmpl");
        holder.tvFuelFilled.setText("Fuel Filled: " + String.valueOf(mileageModel.getFuelFilled()) + " ltr");
        holder.tvDateFilled.setText("Date Filled: " + mileageModel.getDateFilled());
        holder.tvDistanceTravelled.setText("Distance Travelled: " + String.valueOf(mileageModel.getDisctancetravelled()) + " km");
    }

    @Override
    public int getItemCount() {
        return mileageData.size();
    }

    //Reference to individual cards and its elements
    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMileage, tvFuelFilled, tvDateFilled, tvDistanceTravelled;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateFilled = itemView.findViewById(R.id.date_filled);
            tvFuelFilled = itemView.findViewById(R.id.fuel_filled);
            tvMileage = itemView.findViewById(R.id.mileage_value);
            tvDistanceTravelled = itemView.findViewById(R.id.distance_travelled);
        }
    }

}
