package com.example.triviagame.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.triviagame.DBService;
import com.example.triviagame.R;

public class StartActivity extends AppCompatActivity {

    private Animation topAnim, bottomAnim;
    public static MediaPlayer musicGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        playMusic();
        DBService DB = DBService.getSingleInstance();
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 4000);

    }

    private void playMusic() {
        musicGame = MediaPlayer.create(this, R.raw.game_music);
        musicGame.setLooping(true);
        musicGame.start();
    }

}