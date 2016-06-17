package com.app.sam.reactiongame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends FragmentActivity {

    // *** Purple is magenta, missing orange & pink ***
    final static String[] OPTIONS = {"black", "magenta", "blue", "cyan", "green", "yellow", "red"};

    long time;
    int totalTime = 0;
    long interval = 10;
    long timeOut = 4000;
    int points = 0;
    TimerTask timerAction;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button[] options;
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<Integer> aColors = new ArrayList<>();
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();
    int wrongIndex;
    int corIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startGame(chooseColors(), chooseColors());
    }

    @Override
    public void onBackPressed() {
        // Causes android 'Back' button to do nothing
    }

    /*
    Chooses 4 random colors from the global color options and returns them as
    an array of strings
     */
    private String[] chooseColors() {
        String[] colors = new String[4];
        int count = 0;
        while (colors[3] == null) {
            int rand = (int) Math.round((Math.random() * (OPTIONS.length - 1)));
            String option = OPTIONS[rand];
            if (!Arrays.asList(colors).contains(option)) {
                colors[count] = option;
                count++;
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
        TextView instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText(Html.fromHtml(instructions + "<font color='" + color + "'>" + color + "</font>"));
        questions.add(type + " <font color='" + color + "'>" + color + "</font> ");
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);
        options = new Button[]{option1, option2, option3, option4};
        for (int i = 0; i < colors.length; i++) {
            options[i].setText(words[i]);
            options[i].setTextColor(Color.parseColor(colors[i]));
            if (type.equals("color") && colors[i].equals(color)) {
                corIndex = i;
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        answers.add((String) options[corIndex].getText());
                        aColors.add(options[corIndex].getCurrentTextColor());
                        times.add((int) time);
                        timerAction.cancel();
                        totalTime += (int) time;
                        time = 0;
                        if (timeOut > 1250) {
                            timeOut -= 250;
                        }
                        points++;
                        startGame(chooseColors(), chooseColors());
                    }
                });
            } else if (type.equals("word") && words[i].equals(color)) {
                corIndex = i;
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        answers.add((String) options[corIndex].getText());
                        aColors.add(options[corIndex].getCurrentTextColor());
                        times.add((int) time);
                        timerAction.cancel();
                        totalTime += (int) time;
                        time = 0;
                        if (timeOut > 1250) {
                            timeOut -= 250;
                        }
                        points++;
                        startGame(chooseColors(), chooseColors());
                    }
                });
            } else {
                options[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonPress(v);
                        answers.add((String) options[wrongIndex].getText());
                        aColors.add(options[wrongIndex].getCurrentTextColor());
                        times.add((int) time);
                        timerAction.cancel();
                        time = 0;
                        timeOut = 4000;
                        gameLoss();
                    }
                });
            }
        }
        startLossTimer();
    }

    // Helper method to get which incorrect button was pressed on game loss
    public void buttonPress(View v) {
        switch(v.getId()) {
            case R.id.option1: wrongIndex = 0;
                break;
            case R.id.option2: wrongIndex = 1;
                break;
            case R.id.option3: wrongIndex = 2;
                break;
            case R.id.option4: wrongIndex = 3;
                break;
        }
    }

    // Keeps track of game time, stopping app when time runs out
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
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerAction, interval, interval);
    }

    // Ends game by redirecting to loss activity and saving all stats to be displayed
    private void gameLoss() {
        // If loss resulted from timer running out
        if (questions.size() != answers.size()) {
            times.add((int) timeOut);
        }
        Intent intent = new Intent(getApplicationContext(), LossActivity.class);
        intent.putExtra("points", points);
        intent.putExtra("totalTime", (double) totalTime);
        intent.putStringArrayListExtra("questions", questions);
        intent.putStringArrayListExtra("answers", answers);
        intent.putIntegerArrayListExtra("colors", aColors);
        intent.putIntegerArrayListExtra("times", times);
        startActivity(intent);
        finish();
    }
}