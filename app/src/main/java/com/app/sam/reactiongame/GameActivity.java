package com.app.sam.reactiongame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    TextView mTextField;
    TextView instrText;
    final static String[] OPTIONS = {"black", "purple", "blue", "cyan", "green", "yellow", "orange", "pink", "red"};

    long time;
    long interval = 10;
    long timeOut = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startCountdown();
    }

    /*
    Creates a 4 second countdown (3,2,1,Go!) that plays before a game starts
     */
    private void startCountdown() {
        mTextField = (TextView) findViewById(R.id.countdownTextView);

        new CountDownTimer(4000, 100) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 1000) {
                    mTextField.setText("Go!");
                } else {
                    mTextField.setText("" + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                mTextField.setText("");
                startGame(chooseColors());
            }
        }.start();
    }

    /*
    Chooses 4 random colors from the global color options and returns them as
    an array of strings
     */
    private String[] chooseColors() {
        String[] colors = new String[4];
        int index = 0;
        while (colors[3] == null) {
            int rand = (int) Math.round(Math.random() * OPTIONS.length);
            String option = OPTIONS[rand];
            if (!Arrays.asList(colors).contains(option)) {
                colors[index] = option;
                index++;
            }
        }
        return colors;
    }

    /*
    Takes in an array of strings of color names and creates them into a game layout.
    If the correct answer isn't chosen within a specified time, the user loses.
     */
    private void startGame(String[] colors) {
        String instructions = "Press the ";
        // returns 0 or 1 w/ 50% chance
        int rand = (int) Math.round(Math.random());
        if (rand == 1) {
            instructions += "color ";
        } else {
            instructions += "word ";
        }
        String color = colors[(int) Math.floor(Math.random() * colors.length)];
        instructions += "" + color;
        instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText(instructions);
        TimerTask timerAction = new TimerTask() {
            public void run() {
                time += interval;
                if (time >= timeOut) {
                    this.cancel();
                    gameLoss();
                    return;
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerAction, interval, interval);
    }

    private void gameLoss() {
        /*instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText("You lose!");*/
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
