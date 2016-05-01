package com.app.sam.reactiongame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LossActivity extends AppCompatActivity {

    int points;
    int time;

    TextView totalPoints;
    TextView totalTime;
    Button retry;

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
                time = 0;
            } else {
                points = data.getInt("points");
                time = data.getInt("totalTime");
            }
        } else {
            points = (int) savedInstanceState.getSerializable("points");
            time = (int) savedInstanceState.getSerializable("totalTime");
        }
        time = time / 1000;
    }

    private void displayData() {
        retry = (Button) findViewById(R.id.retry);
        totalPoints = (TextView) findViewById(R.id.totalPoints);
        totalTime = (TextView) findViewById(R.id.totalTime);
        totalPoints.setText(getString(R.string.points_message, points));
        totalTime.setText(getString(R.string.time_message, time));
        retry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CountdownActivity.class);
                startActivity(intent);
            }
        });
    }
}
