package com.example.triviagame;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuizService{

    private  List<Question> questionList;
    private  DatabaseReference databaseReference;
    private int counter = 1;

    public QuizService() {
        questionList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Java");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Question question = dataSnapshot.getValue(Question.class);
                    questionList.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
