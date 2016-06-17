package com.app.sam.reactiongame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LossActivity extends AppCompatActivity {

    int points;
    double time;
    double avgTime;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<Integer> colors;
    ArrayList<Integer> times;

    TextView totalPoints;
    TextView totalTime;
    TextView avgTimeView;
    TextView newRecord;
    Button retry;
    TextView history;

    private static final String MY_PREFS = "rec_prefs";

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
                avgTime = 0.0;
                questions = null;
                answers = null;
                colors = null;
                times = new ArrayList<>();
                times.add(0);
            } else {
                points = data.getInt("points");
                time = data.getDouble("totalTime");
                avgTime = 0.0;
                times = data.getIntegerArrayList("times");
                questions = data.getStringArrayList("questions");
                answers = data.getStringArrayList("answers");
                colors = data.getIntegerArrayList("colors");
            }
        } else {
            points = (int) savedInstanceState.getSerializable("points");
            time = (double) savedInstanceState.getSerializable("totalTime");
            avgTime = 0.0;
            times = savedInstanceState.getIntegerArrayList("times");
            questions = savedInstanceState.getStringArrayList("questions");
            answers = savedInstanceState.getStringArrayList("answers");
            colors = savedInstanceState.getIntegerArrayList("colors");
        }
        time = time / 1000;
        int curTime = 0;
        if (times.size() > 1) {
            for (int i = 0; i < times.size(); i++) {
                curTime += times.get(i);
            }
            avgTime = (curTime / 1000.0) / times.size();
        } else {
            avgTime = 0.0;
        }
        /* if (!Double.isNaN(avgTime)) {
            avgTime = Math.floor(((time / points) * 100)) / 100;
        } */
        SharedPreferences stats = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor statsEditor = stats.edit();
        int currentHighScore = stats.getInt("high_score", 0);
        double currentAvg = Double.parseDouble(stats.getString("high_average", "0.0"));
        double currentTime = Double.parseDouble(stats.getString("longest_time", "0.0"));
        // When a new high score is achieved
        if (points > currentHighScore) {
            statsEditor.putInt("high_score", points);
            statsEditor.putString("longest_time", String.format("%.2f", time));
            statsEditor.putString("high_average", String.format("%.2f", avgTime));
            statsEditor.apply();
            newRecord = (TextView) findViewById(R.id.new_record);
            newRecord.setVisibility(View.VISIBLE);
            newRecord.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RecordsActivity.class);
                    startActivity(intent);
                }
            });
        // When the high score remains the same but the old times are beat
        } else if (points == currentHighScore && (avgTime > currentAvg || time < currentTime)) {
            statsEditor.putString("longest_time", String.format("%.2f", time));
            statsEditor.putString("high_average", String.format("%.2f", avgTime));
            statsEditor.apply();
            newRecord = (TextView) findViewById(R.id.new_record);
            newRecord.setVisibility(View.VISIBLE);
            newRecord.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RecordsActivity.class);
                    startActivity(intent);
                }
            });
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
        history = (TextView) findViewById(R.id.view_history);
        history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent historyIntent = new Intent(getApplicationContext(), GameHistoryActivity.class);
                historyIntent.putExtra("points", points);
                historyIntent.putExtra("totalTime", time);
                historyIntent.putStringArrayListExtra("questions", questions);
                historyIntent.putStringArrayListExtra("answers", answers);
                historyIntent.putIntegerArrayListExtra("times", times);
                historyIntent.putIntegerArrayListExtra("colors", colors);
                startActivity(historyIntent);
            }
        });
    }
}
