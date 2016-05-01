package com.app.sam.reactiongame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CountdownActivity extends AppCompatActivity {

    TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        startCountdown();
    }

    /*
    Creates a 4 second countdown (3,2,1,Go!) that plays before a game starts
     */
    private void startCountdown() {
        countText = (TextView) findViewById(R.id.countdown_text_view);
        countText.setVisibility(View.VISIBLE);

        new CountDownTimer(4000, 100) {

            public void onTick(long millisUntilFinished) {
                int sec = (int) millisUntilFinished / 1000;
                if (millisUntilFinished <= 1000) {
                    countText.setTextColor(getColor(R.color.go_message));
                    countText.setText(getString(R.string.countdown_go));
                } else {
                    countText.setText(getString(R.string.countdown, sec));
                }
            }

            public void onFinish() {
                countText.setText("");
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
