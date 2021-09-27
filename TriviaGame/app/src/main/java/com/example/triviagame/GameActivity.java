package com.example.triviagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView question;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Question currQuestion = new Question();
    private final List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question = findViewById(R.id.textView_question);
        option1 = findViewById(R.id.button_option1);
        option2 = findViewById(R.id.button_option2);
        option3 = findViewById(R.id.button_option3);
        option4 = findViewById(R.id.button_option4);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Java");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                currQuestion = snapshot.getValue(Question.class);
                System.out.println(currQuestion.getQuestion());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error.getCode());
            }
        });
    }

    private void handleQuestionDataFromDB(Question question){
        this.question.setText(question.getQuestion());
        this.option1.setText(question.getOption1());
        this.option2.setText(question.getOption2());
        this.option3.setText(question.getOption3());
        this.option4.setText(question.getOption4());
        System.out.println(question.getQuestion());
        System.out.println(question.getOption1());
        System.out.println(question.getOption2());
        System.out.println(question.getOption3());
    }


}