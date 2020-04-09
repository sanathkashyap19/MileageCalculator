package com.sanath.mileage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanath.mileage.adapter.MileageCardAdapter;
import com.sanath.mileage.adapter.MileageDatabaseAdapter;
import com.sanath.mileage.model.MileageModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//This activity allows the user to view all the previous entries of mileage and also the total average mileage
public class MainActivity extends AppCompatActivity {

    RecyclerView rvMileageList;
    RecyclerView.Adapter adapterMileageList;
    FloatingActionButton fabAddFuel;
    TextView tvAvgMileage;
    MileageDatabaseAdapter mileageDatabaseAdapter;

    //Array containing the data models to be displayed in the cards. Model is present in MileageModel.java
    List<MileageModel> mileageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create SharedPreferences to store the average mileage
        SharedPreferences mileagePreferences = getSharedPreferences("mileage", 0);
        float avgMileage = mileagePreferences.getFloat("mileage", 0);

        tvAvgMileage = findViewById(R.id.tv_avg_mileage);

        tvAvgMileage.setText("Avg Mileage: " + avgMileage + " kmpl");

        //Initialize RecyclerView containing the CardView which displays mileage
        rvMileageList = findViewById(R.id.mileage_list);

        //Initialize FloatingActionButton which will take user to AddFuelActivity
        fabAddFuel = findViewById(R.id.fab_add_fuel);

        mileageDatabaseAdapter = new MileageDatabaseAdapter(this);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        rvMileageList.setHasFixedSize(true);

        //On click function for FloatingActionButton
        fabAddFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes to AddFuelActivity
                startActivity( new Intent(getApplicationContext(), AddFuelActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create RecyclerView after going to AddFuelActivity and returning
        createRecyclerView();
    }

    private void createRecyclerView() {

        //Get average mileage
        SharedPreferences mileagePreferences = getSharedPreferences("mileage", 0);
        float avgMileage = mileagePreferences.getFloat("mileage", 0);

        tvAvgMileage = findViewById(R.id.tv_avg_mileage);

        //Set average mileage text
        tvAvgMileage.setText("Avg Mileage: " + avgMileage + " kmpl");

        mileageDatabaseAdapter.Open();

        //Fetch records stored in database
        Cursor cursor = mileageDatabaseAdapter.getQueryResult("SELECT * FROM MILEAGE");
        cursor.moveToFirst();
        mileageList.clear();

        for (int i = 0; i < cursor.getCount(); i++) {
            MileageModel mileageModel = new MileageModel(cursor.getFloat(4), cursor.getFloat(2), cursor.getFloat(3), cursor.getString(1));
            mileageList.add(mileageModel);
            cursor.moveToNext();
        }

        mileageDatabaseAdapter.Close();

        //Set layout manager for RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMileageList.setLayoutManager(mLayoutManager);
        //Set adapter for RecyclerView
        adapterMileageList = new MileageCardAdapter(this, mileageList);
        rvMileageList.setAdapter(adapterMileageList);
    }
}
