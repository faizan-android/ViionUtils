package com.viiontech.repository;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.viiontech.utils.ViionDateFormatter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final String  TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViionDateFormatter dateFormatter = ViionDateFormatter.getInstance();
        Log.e(TAG, dateFormatter.convertTimeStamp("2020-03-12",ViionDateFormatter.simple_date));
        Log.e(TAG, dateFormatter.convertTimeStamp("12-03-2020",ViionDateFormatter.simple_date));
        Log.e(TAG, dateFormatter.convertTimeStamp("12/03/2020",ViionDateFormatter.simple_date));
        Log.e(TAG, dateFormatter.convertTimeStamp("2020/03/12",ViionDateFormatter.simple_date));
        Log.e(TAG, dateFormatter.getMonthName());
        Log.e(TAG, dateFormatter.getFullMonthName());
        Log.e(TAG, dateFormatter.getDayName());
        Log.e(TAG, dateFormatter.getTimeIn12Hours());
        Log.e(TAG, dateFormatter.getTimeIn24Hours());

        Calendar tempCal = Calendar.getInstance();
        Log.e(TAG, dateFormatter.getDifference(tempCal.getTime()));
        tempCal.add(Calendar.DAY_OF_MONTH,-1);
        Log.e(TAG, dateFormatter.getDifference(tempCal.getTime()));
        tempCal.add(Calendar.MONTH,-1);
        Log.e(TAG, dateFormatter.getDifference(tempCal.getTime()));
        Log.e(TAG, dateFormatter.getDifference("20/03/2020"));
    }
}
