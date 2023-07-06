package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MeasurementDetail extends AppCompatActivity {
    private TextView sysOut, diasOut, heartOut, dateOut, timeOut, commentOut;
    MaterialButton editBtn, deleteBtn;
    int id;
    Bundle extras;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_detail);

        extras = getIntent().getExtras();
        initWidgets();
        setMeasurements();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditMeasurement.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getApplicationContext());
                sqLiteManager.deleteNoteInDB(id);
                Toast.makeText(getApplicationContext(), "Delete Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                finishAffinity();
                startActivity(intent);
            }
        });

    }
    /**
     * This function initializes the card view
     */
    private void initWidgets() {
        linearLayout = findViewById(R.id.detailsLayout);
        sysOut = findViewById(R.id.sysOutput);
        diasOut = findViewById(R.id.diasOutput);
        heartOut = findViewById(R.id.heartOutput);
        dateOut = findViewById(R.id.dateOutput);
        timeOut = findViewById(R.id.timeOutput);
        commentOut = findViewById(R.id.commentOutput);
        editBtn = findViewById(R.id.editB);
        deleteBtn = findViewById(R.id.deleteB);
    }
    /**
     * This function loads data from array and set them in the view
     */
    private void setMeasurements() {
        if(extras != null)
            id = extras.getInt("id");

        for(Measurement mes : Measurement.measurementArrayList)
        {
            if(mes.getId() == id)
            {
                sysOut.setText(mes.getSystolicPressure());
                diasOut.setText(mes.getDiastolicPressure());
                heartOut.setText(mes.getHeartRate());
                dateOut.setText(mes.getDate());
                timeOut.setText(mes.getTime());
                commentOut.setText(mes.getComment());
                int sys, dia;
                sys = Integer.parseInt(mes.getSystolicPressure());
                dia = Integer.parseInt(mes.getDiastolicPressure());
                if(sys < 90 || sys > 140 || dia < 60 || dia > 90)
                    linearLayout.setBackgroundResource(R.drawable.rounded_corner_red);
            }
        }
    }
}
