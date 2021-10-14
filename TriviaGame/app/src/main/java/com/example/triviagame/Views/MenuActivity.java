package com.example.triviagame.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.triviagame.Preference;
import com.example.triviagame.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    private Button startGame;
    private Button tableScore;
    private Button settings;
    private Button logout;
    private ImageView lang_btn;
    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        clickSound = MediaPlayer.create(this, R.raw.correct_answer_sound);

        startGame = findViewById(R.id.start_game_btn);
        tableScore = findViewById(R.id.table_score_btn);
        settings = findViewById(R.id.setting_btn);
        logout = findViewById(R.id.logout);
        lang_btn = findViewById(R.id.language_btn);


        //click start game button
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
                finish();
            }
        });

        //click logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.musicGame.stop();
                clickSound.start();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this, StartActivity.class));
                finish();
            }
        });


        //click tableScore button
        tableScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this, TableScoreActivity.class));
                finish();
            }
        });

        //click settings button
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                startActivity(new Intent(MenuActivity.this, Preference.class));
                finish();
            }
        });

        lang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        final String[] listLanguages = {"English", "עברית", "franche"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(listLanguages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("iw");
                    recreate();
                } else if (i == 2) {
                    setLocale("fr");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}