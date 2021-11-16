package com.example.colorblindtest;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class Rank {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String username;

    @ColumnInfo(name="High_Score")
    private int highScore;

    @ColumnInfo(name="Hour")
    private int hour;

    @ColumnInfo(name="Minute")
    private int minute;

    @ColumnInfo(name="Second")
    private int second;
    public Rank(String username, int highScore,int hour,int minute,int second) {
        this.username = username;
        this.highScore = highScore;
        this.hour=hour;
        this.minute=minute;
        this.second=second;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
