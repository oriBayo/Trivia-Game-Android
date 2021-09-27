package com.example.triviagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        register = findViewById(R.id.register_btn);
        login = findViewById(R.id.login_btn);

        // click register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
                finish();
            }
        });

        // Add Questions

//        question = new Question("Methods such as reverse, shuffle are offered in:",
//        "Object", "Collection", "Collections", "Apache Commons Collections",3);
//        FirebaseDatabase.getInstance().getReference().child("Java").child("Question 2").setValue(question);

        // click login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

}