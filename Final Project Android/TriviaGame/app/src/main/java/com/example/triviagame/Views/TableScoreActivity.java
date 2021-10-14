package com.example.triviagame.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.triviagame.DBService;
import com.example.triviagame.R;
import com.example.triviagame.ScoreAdapter;

public class TableScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScoreAdapter scoreAdapter;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_score);
        recyclerView = findViewById(R.id.recycler_view);
        backBtn = findViewById(R.id.backBtn);
        setRecyclerView();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TableScoreActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(this, DBService.getSingleInstance().getScoreList());
        recyclerView.setAdapter(scoreAdapter);
    }

}