package com.example.countdishes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button nextButton;
    private List<String> foodPortions = new ArrayList<>();
    public static final String FOODPORTIONS = "FOODPORTIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        nextButton = findViewById(R.id.btn_next);
        linearLayout = findViewById(R.id.layout_order);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numOfCheckBox = linearLayout.getChildCount();
                for (int i = 0; i < numOfCheckBox; i++) {
                    View v = linearLayout.getChildAt(i);
                    CheckBox checkBox = (CheckBox) v;
                    if (((CheckBox) v).isChecked()) {
                        foodPortions.add(checkBox.getText().toString());
                    }


                }

                Intent intent = new Intent(OrderActivity.this, OrderActivity2.class);
                intent.putExtra(FOODPORTIONS, (ArrayList<String>) foodPortions);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrderActivity.this, MainActivity.class));
        finish();
    }

}