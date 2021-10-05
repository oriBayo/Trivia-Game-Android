package com.example.triviagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

//    private final QuizService quizService = new QuizService();

    private List<Question> questionList = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private ImageView backBtn;
    private TextView timer;
    private TextView selectedTopicName;
    private TextView questions;
    private TextView question;
    private AppCompatButton option1,option2,option3,option4;
    private AppCompatButton nextBtn;
    private Timer quizTimer;
    private int totalTimeInMins = 1;
    private int seconds = 0;
    private int points = 0;

    private int currentQuestionPosition = 1;
    private String currentAnswer;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        backBtn = findViewById(R.id.backBtn);
        timer = findViewById(R.id.timer);
        selectedTopicName = findViewById(R.id.topicName);
        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.next_btn);
        

        loadingDialog = new Dialog(GameActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progressbar_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        databaseReference.child("Java").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.exists()){
                        Question questionFromDB = dataSnapshot.getValue(Question.class);
                        questionList.add(questionFromDB);
                    }
                }
                Question q = questionList.get(0);
                question.setText(q.getQuestion());
                option1.setText(q.getOption1());
                option2.setText(q.getOption2());
                option3.setText(q.getOption3());
                option4.setText(q.getOption4());
                currentAnswer = q.getCorrectAnswer();
                loadingDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");

        selectedTopicName.setText(getSelectedTopicName);

        startTimer(timer);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quizTimer.purge();
                quizTimer.cancel();
                startActivity(new Intent(GameActivity.this,MenuActivity.class));
                finish();
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option1.getText().toString().equals(currentAnswer)){
                    points++;
                }
                setRound();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option2.getText().toString().equals(currentAnswer)){
                    points++;
                }
                setRound();

            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option3.getText().toString().equals(currentAnswer)){
                    points++;
                }
                setRound();
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option4.getText().toString().equals(currentAnswer)){
                    points++;
                }
                setRound();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void startTimer(TextView timerTextView){
        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(seconds == 0){
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins == 0){
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(GameActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GameActivity.this,QuizResultActivity.class);
//                    intent.putExtra("correct",quizService.getCorrectAnswers());
//                    intent.putExtra("inCorrect",quizService.getInCorrectAnswers());
                    startActivity(intent);
                    finish();
                }else{
                    seconds--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0" + finalMinutes;
                        }
                        if(finalSeconds.length() == 1){
                            finalSeconds = "0" + finalSeconds;
                        }
                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });
            }
        },1000,1000);
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();
        startActivity(new Intent(GameActivity.this,MenuActivity.class));
        finish();
    }

    private void setRound(){
        Question question = questionList.get(currentQuestionPosition++);
        this.question.setText(question.getQuestion());
        this.option1.setText(question.getOption1());
        this.option2.setText(question.getOption2());
        this.option3.setText(question.getOption3());
        this.option4.setText(question.getOption4());
        this.currentAnswer = question.getCorrectAnswer();

    }


    private void checkingQuestion(String choicePlayer){
        if(choicePlayer == currentAnswer){

        }
    }


}