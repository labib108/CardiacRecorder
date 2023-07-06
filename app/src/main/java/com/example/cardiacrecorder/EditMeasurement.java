package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
public class EditMeasurement extends AppCompatActivity  {
    private EditText sysOut, diasOut, heartOut, dateOut, timeOut, commentOut;
    MaterialButton updateBtn, cancelBtn;
    int id;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_measurement);

        sysOut = findViewById(R.id.systolicPressure);
        diasOut = findViewById(R.id.diastolicPressure);
        heartOut = findViewById(R.id.heartRate);
        dateOut = findViewById(R.id.date);
        timeOut = findViewById(R.id.time);
        commentOut = findViewById(R.id.comment);
        updateBtn = findViewById(R.id.updateM);
        cancelBtn = findViewById(R.id.cancel);
        extras = getIntent().getExtras();

        setMeasurements();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getApplicationContext());

                Boolean okay = true;

                String sysP = sysOut.getText().toString();
                String diaP = diasOut.getText().toString();
                String heartR = heartOut.getText().toString();
                String date = dateOut.getText().toString();
                String time = timeOut.getText().toString();
                String comment = commentOut.getText().toString();

                if(sysP.length() <= 0){
                    sysOut.requestFocus();
                    sysOut.setError("Enter systolic pressure");
                    okay = false;
                }
                else if(diaP.length() <= 0){
                    diasOut.requestFocus();
                    diasOut.setError("Enter diastolic pressure");
                    okay = false;
                }
                else if(heartR.length() <= 0){
                    heartOut.requestFocus();
                    heartOut.setError("Enter heart rate");
                    okay = false;
                }
                else if(date.length() <= 0){
                    dateOut.requestFocus();
                    dateOut.setError("Enter a date");
                    okay = false;
                }
                else if(time.length() <= 0) {
                    timeOut.requestFocus();
                    timeOut.setError("Enter a time");
                    okay = false;
                }

                if(okay)
                {
                    Measurement measurement = new Measurement(date, time, sysP, diaP, heartR, comment);
                    measurement.setId(id);
                    sqLiteManager.updateNoteInDB(measurement);

                    Toast.makeText(getApplicationContext(), "Update Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    finishAffinity();
                    startActivity(intent);
                }
            }
        });

    }

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
            }
        }
    }
}