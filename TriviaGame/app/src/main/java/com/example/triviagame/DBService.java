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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DBService {

    private static DBService singleInstance;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<Score> scoreList;
    private final List<Question>questionList;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private DBService(){
        scoreList = collectData("scores",Score.class);
        questionList = collectData("Java",Question.class);
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public static DBService getSingleInstance(){
        if(singleInstance == null){
            lock.writeLock().lock();
            if(singleInstance == null){
                singleInstance = new DBService();
                lock.writeLock().unlock();
            }
        }
        return singleInstance;
    }

    private <T> List<T> collectData (String childName,Class<T> tClass){
        List<T> listOfData = new ArrayList<>();
        reference.child(childName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.exists()){
                        T data = dataSnapshot.getValue(tClass);
                        listOfData.add(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return listOfData;
    }

    public void saveScoresData(Score score){
        reference.child("scores").child("score " + scoreList.size()).setValue(score);
        scoreList.add(score);
    }
}
