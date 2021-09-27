package com.example.triviagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        register = findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();

        // click register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                // field not empty
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();

                // password at least 6 characters
                }else if(txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();

                // register
                }else{
                    registerUser(txt_email, txt_password);
                }
            }
        });

    }

    /**
     *
     * @param email
     * @param password
     */
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register user successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Register user failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}