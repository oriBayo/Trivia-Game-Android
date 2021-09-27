package com.example.triviagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button startGame;
    private Button tableScore;
    private Button settings;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startGame = findViewById(R.id.start_game_btn);
        tableScore = findViewById(R.id.table_score_btn);
        settings = findViewById(R.id.setting_btn);
        logout = findViewById(R.id.logout);

        //click start game button
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
                finish();
            }
        });

        //click logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this,StartActivity.class));
                finish();
            }
        });
    }
}