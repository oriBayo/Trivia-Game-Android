package com.example.triviagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ContentLoadingProgressBar;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private final int TIME = 20;
    private final List<Question> questionList = DBService.getSingleInstance().getQuestionList();
    private ImageView backBtn;
    private TextView questions,question;
    private AppCompatButton option1,option2,option3,option4;
    private int numOfCorrectAnswer = 0;
    private CountDownTimer countDownTimer;
    private int timerValue = TIME;
    private ContentLoadingProgressBar progressBar;
    private int currentQuestionPosition = 0;
    private String currentAnswer;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        findAllViews();
        initDialog();
        initTimer();
        fetchDataFromDB();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameActivity.this,MenuActivity.class));
                countDownTimer.cancel();
                finish();
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1.getText().toString(),v);
                changeQuestion();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2.getText().toString(),v);
                changeQuestion();
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3.getText().toString(),v);
                changeQuestion();
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option4.getText().toString(),v);
                changeQuestion();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this,MenuActivity.class));
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void changeQuestion(){

        if(currentQuestionPosition == questionList.size() - 1){
            countDownTimer.cancel();
            Intent intent = new Intent(GameActivity.this, WonActivity.class);
            intent.putExtra("correct", numOfCorrectAnswer);
            intent.putExtra("numOfQuestion",questionList.size());
            startActivity(intent);
            finish();
        }
        else{

            playAnimation(question,0,0);
            playAnimation(option1,0,1);
            playAnimation(option2,0,2);
            playAnimation(option3,0,3);
            playAnimation(option4,0,4);

            currentQuestionPosition++;
            questions.setText(currentQuestionPosition + "/" + questionList.size());
            Question question = questionList.get(currentQuestionPosition);
            this.currentAnswer = question.getCorrectAnswer();
        }
    }

    private void checkAnswer(String playerOption,View view){
        if(playerOption.equals(currentAnswer)){
            ((AppCompatButton)view).setBackgroundResource(R.drawable.correct_answer_background);
            ((AppCompatButton)view).setTextColor(Color.WHITE);
            numOfCorrectAnswer++;

        }
        else{

            ((AppCompatButton)view).setBackgroundResource(R.drawable.wrong_answer_background);
            ((AppCompatButton)view).setTextColor(Color.WHITE);


            if(currentAnswer.equals(option1.getText().toString())){
                option1.setBackgroundResource(R.drawable.correct_answer_background);
                option1.setTextColor(Color.WHITE);
            }
            else if(currentAnswer.equals(option2.getText().toString())){
                option2.setBackgroundResource(R.drawable.correct_answer_background);
                option2.setTextColor(Color.WHITE);
            }

            else if(currentAnswer.equals(option3.getText().toString())){
                option3.setBackgroundResource(R.drawable.correct_answer_background);
                option3.setTextColor(Color.WHITE);
            }

            else if(currentAnswer.equals(option4.getText().toString())){
                option4.setBackgroundResource(R.drawable.correct_answer_background);
                option4.setTextColor(Color.WHITE);
            }

        }
    }

    private void findAllViews(){
        backBtn = findViewById(R.id.backBtn);
        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        progressBar = findViewById(R.id.progressBarTimer);
    }

    private void initDialog(){
        loadingDialog = new Dialog(GameActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progressbar_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
    }

    private void fetchDataFromDB(){
        Question q = questionList.get(currentQuestionPosition++);
        question.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
        currentAnswer = q.getCorrectAnswer();

        loadingDialog.cancel();
        countDownTimer.start();
    }

    private void initTimer(){
        long duration = TimeUnit.MINUTES.toMillis(1);
        countDownTimer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue -= 1;
                progressBar.setProgress(timerValue);
                if(timerValue == 0) {
                    cancel();
                    onFinish();
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameActivity.this, TimesUpActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void playAnimation(View view,final int value,int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionList.get(currentQuestionPosition).getQuestion());
                                    break;
                                case 1:
                                    ((TextView)view).setText(questionList.get(currentQuestionPosition).getOption1());
                                    break;
                                case 2:
                                    ((TextView)view).setText(questionList.get(currentQuestionPosition).getOption2());
                                    break;
                                case 3:
                                    ((TextView)view).setText(questionList.get(currentQuestionPosition).getOption3());
                                    break;
                                case 4:
                                    ((TextView)view).setText(questionList.get(currentQuestionPosition).getOption4());
                                    break;
                            }
                            if(viewNum != 0){
                                ((TextView)view).setBackgroundResource(R.drawable.round_back_white_stroke2);
                                ((TextView)view).setTextColor(Color.parseColor("#1F6BB8"));
                            }
                            playAnimation(view,1,viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

}