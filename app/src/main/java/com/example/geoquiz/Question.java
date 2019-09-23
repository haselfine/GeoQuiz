package com.example.geoquiz;

public class Question {

    public int getTextResId() { //gets text res id
        return mTextResId;
    }

    public void setTextResId(int textResId) { //sets text res id
        mTextResId = textResId;
    }

    private int mTextResId;

    public boolean isAnswerTrue() { //gets bool AnswerTrue
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) { //sets true or false
        mAnswerTrue = answerTrue;
    }

    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){ //returns members of class
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }
}
