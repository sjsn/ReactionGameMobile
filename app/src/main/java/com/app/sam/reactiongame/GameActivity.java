package com.app.sam.reactiongame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    TextView instrText;
    //TextView lossMessage;
    LinearLayout row1;
    LinearLayout row2;
    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;

    final static String[] OPTIONS = {"black", "purple", "blue", "cyan", "green", "yellow", "orange", "pink", "red"};

    long time;
    long interval = 10;
    long timeOut = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startGame(chooseColors());
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
    private void startGame(String[] colors) {
        String instructions = "Press the ";
        String type;
        // returns 0 or 1 w/ 50% chance
        int rand = (int) Math.round(Math.random());
        if (rand == 1) {
            type = "color";
            instructions += type + " ";
        } else {
            type = "word";
            instructions += type + " ";
        }
        String color = colors[(int) Math.floor(Math.random() * colors.length)];
        instructions += "" + color;
        if (type == "color") {

        } else {

        }
        option1 = (TextView) findViewById(R.id.option1);
        option2 = (TextView) findViewById(R.id.option2);
        option3 = (TextView) findViewById(R.id.option3);
        option4 = (TextView) findViewById(R.id.option4);
        TextView[] options = new TextView[]{option1, option2, option3, option4};
        for (int i = 0; i < colors.length; i++) {
            options[i].setText(colors[i]);
        }
        instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText(instructions);
        TimerTask timerAction = new TimerTask() {
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

    private void gameLoss() {
        /*instrText = (TextView) findViewById(R.id.instr_text);
        instrText.setText("You lose!");*/

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
