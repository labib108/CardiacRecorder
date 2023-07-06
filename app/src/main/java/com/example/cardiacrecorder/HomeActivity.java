package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private recyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.measurementListView);
        Measurement.measurementArrayList = new ArrayList<>();

        loadFromDBToMemory();
        setAdapter();

        MaterialButton addNewMeasurementBtn = findViewById(R.id.addNewMeasurement);
        addNewMeasurementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddMeasurement.class));
            }
        });
    }

    /**
     * This function loads data from database into the recycler view
     */
    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateMeasurementListArray();
    }

    /**
     * This function initiates the adapter and populates the recycler view
     */
    private void setAdapter() {
        setOnClickListener();
        recyclerAdapter adapter = new recyclerAdapter(Measurement.measurementArrayList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new recyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MeasurementDetail.class);
                intent.putExtra("id", Measurement.measurementArrayList.get(position).getId());
                startActivity(intent);
            }
        };
    }

}
