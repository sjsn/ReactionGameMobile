package com.app.sam.reactiongame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private static final String MYPREFS = "rec_prefs";
    SharedPreferences.Editor statsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();
        setupResetButton();
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupResetButton() {
        SharedPreferences stats = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        statsEditor = stats.edit();
        Button clear = (Button) findViewById(R.id.reset_stats);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsEditor.putInt("high_score", 0);
                statsEditor.putInt("longest_time", 0);
                statsEditor.apply();
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
