package com.example.cardiacrecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "MeasureDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Measurements";
    private static final String COUNTER = "Counter";

    private static final String DATE_FIELD = "date";
    private static final String TIME_FIELD = "time";
    private static final String SYS_FIELD = "sys";
    private static final String DIA_FIELD = "dia";
    private static final String HEART_FIELD = "heart";
    private static final String COMMENT_FIELD = "comment";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(SYS_FIELD)
                .append(" TEXT, ")
                .append(DIA_FIELD)
                .append(" TEXT, ")
                .append(HEART_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT, ")
                .append(TIME_FIELD)
                .append(" TEXT, ")
                .append(COMMENT_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addMeasurementToDatabase(Measurement measurement)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SYS_FIELD, measurement.getSystolicPressure());
        contentValues.put(DIA_FIELD, measurement.getDiastolicPressure());
        contentValues.put(HEART_FIELD, measurement.getHeartRate());
        contentValues.put(DATE_FIELD, measurement.getDate());
        contentValues.put(TIME_FIELD, measurement.getTime());
        contentValues.put(COMMENT_FIELD, measurement.getComment());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateMeasurementListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try(Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while(result.moveToNext())
                {
                    int id = result.getInt(0);
                    String sys = result.getString(1);
                    String dia = result.getString(2);
                    String heart = result.getString(3);
                    String date = result.getString(4);
                    String time = result.getString(5);
                    String comment = result.getString(6);
                    Measurement measurement = new Measurement(date, time, sys, dia, heart, comment);
                    measurement.setId(id);
                    Measurement.measurementArrayList.add(measurement);
                }
            }
        }
    }

    public void updateNoteInDB(Measurement measurement)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SYS_FIELD, measurement.getSystolicPressure());
        contentValues.put(DIA_FIELD, measurement.getDiastolicPressure());
        contentValues.put(HEART_FIELD, measurement.getHeartRate());
        contentValues.put(DATE_FIELD, measurement.getDate());
        contentValues.put(TIME_FIELD, measurement.getTime());
        contentValues.put(COMMENT_FIELD, measurement.getComment());

        sqLiteDatabase.update(TABLE_NAME, contentValues, COUNTER + " =? ", new String[]{String.valueOf(measurement.getId())});
    }

    public void deleteNoteInDB(int id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COUNTER + " = ?", new String[]{String.valueOf(id)});
    }
}
