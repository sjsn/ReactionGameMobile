package com.app.sam.reactiongame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordsActivity extends AppCompatActivity {

    private static final String MYPREFS = "rec_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        createRecords();
    }

    private void createRecords() {
        SharedPreferences stats = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        int currentHighScore = stats.getInt("high_score", 0);
        int currentLongestTime = stats.getInt("longest_time", 0);
        double currentAvgTime = stats.getLong("high_average", 0);
        TextView points = (TextView) findViewById(R.id.high_points);
        TextView avg = (TextView) findViewById(R.id.high_avg);
        TextView time = (TextView) findViewById(R.id.high_time);
        String pointString = getString(R.string.high_points, currentHighScore);
        String avgString = getString(R.string.high_avg, String.format("%.2f", currentAvgTime));
        String timeString = getString(R.string.high_time, currentLongestTime);
        points.setText(pointString);
        avg.setText(avgString);
        time.setText(timeString);
    }

}
