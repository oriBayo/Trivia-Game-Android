package com.example.triviagame;

public class Score {
    private String playerName;
    private int points;
    private String date;

    public Score(String playerName, int points, String date) {
        this.playerName = playerName;
        this.points = points;
        this.date = date;
    }

    public Score() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
