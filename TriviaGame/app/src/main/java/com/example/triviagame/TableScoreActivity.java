package com.example.triviagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TableScoreActivity extends AppCompatActivity {

    EditText question;
    EditText option1;
    EditText option2;
    EditText option3;
    EditText option4;
    EditText answer;
    Button btn;
    int pos = 1;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_score);

        question = findViewById(R.id.editTextQuestion);
        option1 = findViewById(R.id.editTextOption1);
        option2 = findViewById(R.id.editTextOption2);
        option3 = findViewById(R.id.editTextOption3);
        option4 = findViewById(R.id.editTextOption4);
        answer = findViewById(R.id.editTextAnswer);
        btn = findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionStr = question.getText().toString();
                String option1Str = option1.getText().toString();
                String option2Str = option2.getText().toString();
                String option3Str = option3.getText().toString();
                String option4Str = option4.getText().toString();
                String answerStr = answer.getText().toString();

                Question q = new Question(questionStr,option1Str,option2Str,option3Str,option4Str,answerStr);
                databaseReference.child("Java").child("question " + pos++).setValue(q);
            }
        });

    }
}