package com.example.countdishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderActivity2 extends AppCompatActivity {

    private List<String> dishesList;
    private Button addOrderButton;
    private LinearLayout layout;
    private List<View> viewList = new ArrayList<>();
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);

        dishesList = (ArrayList<String>) getIntent().getStringArrayListExtra(OrderActivity.FOODPORTIONS);
        addOrderButton = findViewById(R.id.add_order_btn);
        layout = findViewById(R.id.container);

        initTimer();
        timer.start();
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeDishesDialog();
            }
        });

    }

    private void showChangeDishesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity2.this);
        builder.setTitle("Choose Dishes...");
        builder.setSingleChoiceItems(dishesList.toArray(new String[dishesList.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addOrder(dishesList.get(i));
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addOrder(String dishesName) {
        View view = getLayoutInflater().inflate(R.layout.order, null);
        TextView name = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.remove_btn);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        viewList.add(view);

        name.setText(dishesName);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        layout.addView(view);
    }

    private void initTimer() {
        long duration = TimeUnit.MINUTES.toMillis(1);
        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                for (View v : viewList) {
                    TextView name = v.findViewById(R.id.name);
                    Button button = v.findViewById(R.id.remove_btn);
                    ProgressBar progressBar = v.findViewById(R.id.progressBar);
                    progressBar.setProgress(progressBar.getProgress() + 1);
                    if(progressBar.getProgress() == 30){
                        button.setBackground(getResources().getDrawable(R.drawable.button_ready));
                    }else if(progressBar.getProgress() == 60){
                        button.setBackground(getResources().getDrawable(R.drawable.button_done));
                    }
                }
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
    }
}