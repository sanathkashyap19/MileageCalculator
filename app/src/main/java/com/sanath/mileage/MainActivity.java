package com.sanath.mileage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanath.mileage.adapter.MileageCardAdapter;
import com.sanath.mileage.model.MileageModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//This activity allows the user to view all the previous entries of mileage and also the total average mileage
public class MainActivity extends AppCompatActivity {

    RecyclerView rvMileageList;
    RecyclerView.Adapter adapterMileageList;
    FloatingActionButton fabAddFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize RecyclerView containing the CardView which displays mileage
        rvMileageList = findViewById(R.id.mileage_list);

        //Initialize FloatingActionButton which will take user to AddFuelActivity
        fabAddFuel = findViewById(R.id.fab_add_fuel);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        rvMileageList.setHasFixedSize(true);

        //Array containing the data models to be displayed in the cards. Model is present in MileageModel.java
        List<MileageModel> mileageList = new ArrayList<>();
        MileageModel mileageModel = new MileageModel(15, 12, 200, "00/00/0000");
        mileageList.add(mileageModel);
        mileageList.add(mileageModel);
        mileageList.add(mileageModel);
        mileageList.add(mileageModel);
        mileageList.add(mileageModel);
        mileageList.add(mileageModel);

        mileageModel = new MileageModel(20, 12, 200, "00/00/0000");
        mileageList.add(mileageModel);

        //Set layout manager for RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMileageList.setLayoutManager(mLayoutManager);
        //Set adapter for RecyclerView
        adapterMileageList = new MileageCardAdapter(this, mileageList);
        rvMileageList.setAdapter(adapterMileageList);

        //On click function for FloatingActionButton
        fabAddFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes to AddFuelActivity
                startActivity( new Intent(getApplicationContext(), AddFuelActivity.class));
            }
        });
    }
}
