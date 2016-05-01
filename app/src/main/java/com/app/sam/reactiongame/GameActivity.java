package com.app.sam.reactiongame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    TextView instrText;
    Button option1;
    Button option2;
    Button option3;
    Button option4;

    // *** Purple is magenta, missing orange & pink ***
    final static String[] OPTIONS = {"black", "magenta", "blue", "cyan", "green", "yellow", "red"};

    long time;
    int totalTime = 0;
    long interval = 10;
    long timeOut = 4000;
    int points = 0;
    Timer timer;
    TimerTask timerAction;
    //Stack answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startGame(chooseColors(), chooseColors());
    }

    /*
    Chooses 4 random colors from the global color options and returns them as
    an array of strings
     */
    private String[] chooseColors() {
        String[] colors = new String[4];
        int index = 0;
        while (colors[3] == null) {
            int rand = (int) Math.round((Math.random() * (OPTIONS.length - 1)));
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
    private void startGame(String[] colors, String[] words) {
        String instructions = "Press the ";
        String type;
        String color;
        // returns 0 or 1 w/ 50% chance
        int rand = (int) Math.round(Math.random());
        if (rand == 1) {
            type = "color";
            instructions += type + " ";
            color = colors[(int) Math.floor(Math.random() * colors.length)];
        } else {
            type = "word";
            instructions += type + " ";
            color = words[(int) Math.floor(Math.random() * words.length)];
        }
        instructions += "" + color;
        instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText(instructions);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);
        Button[] options = new Button[]{option1, option2, option3, option4};
        for (int i = 0; i < colors.length; i++) {
            options[i].setText(words[i]);
            options[i].setTextColor(Color.parseColor(colors[i]));
            if (type.equals("color") && colors[i].equals(color)) {
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //answers.push(options[i]);
                        timerAction.cancel();
                        totalTime += (int) time;
                        time = 0;
                        points++;
                        startGame(chooseColors(), chooseColors());
                    }
                });
            } else if (type.equals("word") && words[i].equals(color)) {
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //answers.push(options[i]);
                        timerAction.cancel();
                        totalTime += (int) time;
                        time = 0;
                        points++;
                        startGame(chooseColors(), chooseColors());
                    }
                });
            } else {
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        timerAction.cancel();
                        time = 0;
                        gameLoss();
                    }
                });
            }
        }
        startLossTimer();
    }

    private void startLossTimer() {
        timerAction = new TimerTask() {
            public void run() {
                time += interval;
                if (time >= timeOut) {
                    this.cancel();
                    gameLoss();
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerAction, interval, interval);
    }

    private void gameLoss() {
        Intent intent = new Intent(getApplicationContext(), LossActivity.class);
        intent.putExtra("points", points);
        intent.putExtra("totalTime", totalTime);
        startActivity(intent);
    }
}
