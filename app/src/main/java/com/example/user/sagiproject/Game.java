package com.example.user.sagiproject;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Game implements Comparable<Game> {
    private String date;
    private int score;

    public Game(String date, int score)
    {
        this.date = date;
        this.score = score;
    }



    @Override
    public int compareTo(Game other) {
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            date1 = format.parse(this.date);
            date2 = format.parse(other.date);
        }
        catch (Exception e) {
        }


            return date1.compareTo(date2);

    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "Score: " + score +
                ", Date: " + date;
    }
}
