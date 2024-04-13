package com.example.eggert_hoppens_project2.DB.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.Arrays;

@Entity (tableName = AppDataBase.QUESTION_TABLE)
public class Question {
    //----------------------------------------------------------------Fields
    @PrimaryKey (autoGenerate = true)
    private int mQuestionKey;

    private String mType;
    private String mDifficulty;
    private String mCategory;
    private String mQuestion;
    private String mCorrectAnswer;
    private String[] mWrongAnswers;

    //----------------------------------------------------------------Constructor
    public Question(String type, String difficulty, String category, String question,
                    String rightAnswer, String[] wrongAnswer) {
        mType = type;
        mDifficulty = difficulty;
        mCategory = category;
        mQuestion = question;
        mCorrectAnswer = rightAnswer;
        mWrongAnswers = wrongAnswer;
    }

    //----------------------------------------------------------------ToString
    @Override
    public String toString() {
        return "Question{" +
                "mQuestionKey=" + mQuestionKey +
                ", mType='" + mType + '\'' +
                ", mDifficulty='" + mDifficulty + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mQuestion='" + mQuestion + '\'' +
                ", mRightAnswer='" + mCorrectAnswer + '\'' +
                ", mWrongAnswer=" + Arrays.toString(mWrongAnswers) +
                '}';
    }

    //-----------------------------------------------------------------Get/Set
    public int getQuestionKey() {
        return mQuestionKey;
    }

    public void setQuestionKey(int questionKey) {
        mQuestionKey = questionKey;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(String difficulty) {
        mDifficulty = difficulty;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

    public String[] getWrongAnswers() {
        return mWrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        mWrongAnswers = wrongAnswers;
    }
}
