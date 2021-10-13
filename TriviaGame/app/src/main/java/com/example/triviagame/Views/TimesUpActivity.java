package com.example.triviagame.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.triviagame.R;

public class TimesUpActivity extends AppCompatActivity {

    private LinearLayout btnTryAgain;
    private MediaPlayer loseSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_up);

        loseSound = MediaPlayer.create(this, R.raw.lose_sound);
        loseSound.start();
        btnTryAgain = findViewById(R.id.tryAgainBtn);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimesUpActivity.this, GameActivity.class));
                finish();
            }
        });
    }
}