package com.example.colorblindtest;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ImageReader;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Question{
    private int mImage;
    private int mAnswer;
    private int userInput;

    public Question(int image,int answer){
        mImage=image;
        mAnswer=answer;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int answer) {
        mAnswer = answer;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }


    public int getUserInput() {
        return userInput;
    }

    public void setUserInput(int userInput) {
        this.userInput = userInput;
    }
}
