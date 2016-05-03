package com.app.sam.reactiongame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        String currentLongestTime = stats.getString("longest_time", "0.00");
        String currentAvgTime = stats.getString("high_average", "0.00");
        TextView points = (TextView) findViewById(R.id.high_points);
        TextView avg = (TextView) findViewById(R.id.high_avg);
        TextView time = (TextView) findViewById(R.id.high_time);
        String pointString = getString(R.string.high_points, currentHighScore);
        String avgString = getString(R.string.high_avg, currentAvgTime);
        String timeString = getString(R.string.high_time, currentLongestTime);
        points.setText(pointString);
        avg.setText(avgString);
        time.setText(timeString);
    }

}
