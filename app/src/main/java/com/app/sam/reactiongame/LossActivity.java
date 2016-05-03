package com.app.sam.reactiongame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LossActivity extends AppCompatActivity {

    int points;
    double time;
    double avgTime;

    TextView totalPoints;
    TextView totalTime;
    TextView avgTimeView;
    TextView newRecord;
    Button retry;

    private static final String MYPREFS = "rec_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);
        getData(savedInstanceState);
        displayData();
    }

    private void getData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle data = getIntent().getExtras();
            if (data == null) {
                points = 0;
                time = 0.0;
            } else {
                points = data.getInt("points");
                time = data.getDouble("totalTime");
            }
        } else {
            points = (int) savedInstanceState.getSerializable("points");
            time = (double) savedInstanceState.getSerializable("totalTime");
        }
        time = time / 1000;
        avgTime = Math.floor(((time / points) * 100)) / 100;
        SharedPreferences stats = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor statsEditor = stats.edit();
        int currentHighScore = stats.getInt("high_score", 0);
        if (points > currentHighScore) {
            statsEditor.putInt("high_score", points);
            statsEditor.putString("longest_time", String.format("%.2f", time));
            statsEditor.putString("high_average", String.format("%.2f", avgTime));
            newRecord = (TextView) findViewById(R.id.new_record);
            newRecord.setVisibility(View.VISIBLE);
            statsEditor.apply();
        }
    }

    private void displayData() {
        retry = (Button) findViewById(R.id.retry);
        totalPoints = (TextView) findViewById(R.id.total_points);
        totalTime = (TextView) findViewById(R.id.total_time);
        avgTimeView = (TextView) findViewById(R.id.avg_time);
        totalPoints.setText(getString(R.string.points_message, points));
        totalTime.setText(getString(R.string.time_message, String.format("%.2f", time)));
        avgTimeView.setText(getString(R.string.avg_time_message, String.format("%.2f", avgTime)));
        retry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CountdownActivity.class);
                startActivity(intent);
            }
        });
    }
}
