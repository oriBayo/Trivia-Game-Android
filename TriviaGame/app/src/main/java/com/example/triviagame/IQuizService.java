package com.example.triviagame;

public interface IQuizService {
    int getCorrectAnswers();
    int getInCorrectAnswers();
    Question getQuestion(int index);
}
