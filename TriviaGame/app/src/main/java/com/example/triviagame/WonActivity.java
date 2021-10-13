package com.example.triviagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WonActivity extends AppCompatActivity {

    private CircularProgressBar circularProgressBar;
    private TextView correct;
    private LinearLayout btnPlayAgain;
    private int numOfQuestions, correctAnswer;
    private EditText playerName;
    private MediaPlayer winSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        winSound = MediaPlayer.create(this, R.raw.winner_sound);
        winSound.start();
        correctAnswer = getIntent().getIntExtra("correct", 0);
        numOfQuestions = getIntent().getIntExtra("numOfQuestion", 0);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        correct = findViewById(R.id.correctAnswer);
        btnPlayAgain = findViewById(R.id.tryAgainBtn);
        playerName = findViewById(R.id.playerName);

        circularProgressBar.setProgressMax(numOfQuestions);
        circularProgressBar.setProgress(correctAnswer);
        correct.setText(correctAnswer + "/" + numOfQuestions);

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter myDate = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");
                String formattedDate = date.format(myDate);
                String name = playerName.getText().toString();
                Score score = new Score(name, correctAnswer, formattedDate);
                DBService.getSingleInstance().saveScoresData(score);

                startActivity(new Intent(WonActivity.this, GameActivity.class));
                finish();
            }
        });


    }
}