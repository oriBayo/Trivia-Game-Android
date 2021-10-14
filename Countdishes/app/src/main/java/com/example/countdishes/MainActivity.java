package com.example.countdishes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button englishBtn;
    private Button hebrewBtn;
    private Button russianBtn;
    private Button arabicBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        englishBtn = findViewById(R.id.button_en);
        hebrewBtn = findViewById(R.id.button_he);
        russianBtn = findViewById(R.id.button_rus);
        arabicBtn = findViewById(R.id.button_ar);

        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
                finish();
            }
        });

        hebrewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("iw");
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
                finish();
            }
        });

        russianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("ru");
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
                finish();
            }
        });

        arabicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("ar");
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
                finish();
            }
        });
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