package com.app.sam.reactiongame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {

    int points;
    double time;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<Integer> colors;
    ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        getData(savedInstanceState);
        drawResults();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("questions", questions);
        intent.putStringArrayListExtra("answers", answers);
        intent.putIntegerArrayListExtra("colors", colors);
        intent.putIntegerArrayListExtra("times", times);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void getData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle data = getIntent().getExtras();
            if (data == null) {
                points = 0;
                time = 0.0;
                questions = null;
                times = new ArrayList<>();
                times.add(0);
                answers = null;
                colors = null;
            } else {
                points = data.getInt("points");
                time = data.getDouble("totalTime");
                questions = data.getStringArrayList("questions");
                times = data.getIntegerArrayList("times");
                answers = data.getStringArrayList("answers");
                colors = data.getIntegerArrayList("colors");
            }
        } else {
            points = (int) savedInstanceState.getSerializable("points");
            time = (double) savedInstanceState.getSerializable("totalTime");
            questions = savedInstanceState.getStringArrayList("questions");
            times = savedInstanceState.getIntegerArrayList("times");
            answers = savedInstanceState.getStringArrayList("answers");
            colors = savedInstanceState.getIntegerArrayList("colors");
        }
    }

    private void drawResults() {
        TableLayout historyTable = (TableLayout) findViewById(R.id.history_table);
        TableRow header = new TableRow(this);
        TextView q = new TextView(this);
        TextView a = new TextView(this);
        TextView t = new TextView(this);
        TextView[] headers = {q, a, t};
        q.setText(R.string.history_q);
        a.setText(R.string.history_a);
        t.setText(R.string.history_t);
        for (TextView head : headers) {
            head.setTextSize(15);
            head.setTextColor(ContextCompat.getColor(this, R.color.history_header));
            header.addView(head);
        }
        historyTable.addView(header);
        for (int i = 0; i < questions.size(); i++) {
            TableRow response = new TableRow(this);
            response.setPadding(5, 5, 5, 5);
            TextView question = new TextView(this);
            TextView answer = new TextView(this);
            TextView time = new TextView(this);
            String word;
            if (i < answers.size()) {
                word = "<font color='" + colors.get(i) + "'>" + answers.get(i) + "</font>";
            } else {
                word = "<font color='black'>nothing</font";
            }
            question.setText(Html.fromHtml(questions.get(i)));
            answer.setText(Html.fromHtml(word));
            time.setText(getString(R.string.history_times, String.format("%.2f", (times.get(i)) / 1000.0)));
            response.addView(question);
            response.addView(answer);
            response.addView(time);
            historyTable.addView(response);
        }
    }
}
