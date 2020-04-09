package com.sanath.mileage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sanath.mileage.adapter.MileageDatabaseAdapter;

import java.util.Date;

public class AddFuelActivity extends AppCompatActivity {

    MileageDatabaseAdapter mileageDatabaseAdapter;
    EditText etFuelFilled, etDistanceTravelled;
    Button btSaveMileage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel);

        etFuelFilled = findViewById(R.id.et_fuel_filled);
        etDistanceTravelled = findViewById(R.id.et_distance_travelled);
        btSaveMileage = findViewById(R.id.bt_save_mileage);

        mileageDatabaseAdapter = new MileageDatabaseAdapter(this);

        btSaveMileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float fuelFilled = Float.parseFloat(etFuelFilled.getText().toString());
                float distanceTravelled = Float.parseFloat(etDistanceTravelled.getText().toString());

                //Calculate new average mileage
                calcMileage(fuelFilled, distanceTravelled);

                mileageDatabaseAdapter.Open();

                //Store new records to database
                mileageDatabaseAdapter.InsertMileage(new Date().toString(), fuelFilled, distanceTravelled, (distanceTravelled/fuelFilled));

                mileageDatabaseAdapter.Close();

                finish();
            }
        });
    }

    private void calcMileage(float fuelFilled, float distanceTravelled) {

        mileageDatabaseAdapter.Open();

        //Get total number of times fuel filled
        long refills = mileageDatabaseAdapter.getCountOfEntries("MILEAGE");

        mileageDatabaseAdapter.Close();

        //Get current average mileage
        SharedPreferences mileagePreferences = getSharedPreferences("mileage", 0);
        float avgMileage = mileagePreferences.getFloat("mileage", 0);

        //newAvg = ((n * avg) + (x)) / (n+1)
        float newAvgMileage = ((refills * avgMileage) + (distanceTravelled/fuelFilled)) / (refills + 1);

        //Commit new average mileage
        SharedPreferences.Editor mileagePreferencesEditor = mileagePreferences.edit();
        mileagePreferencesEditor.putFloat("mileage", newAvgMileage).apply();
    }
}
