package com.example.eggert_hoppens_project2.DB.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

@Entity (tableName = AppDataBase.QUESTION_TABLE)
public class Question {
    //----------------------------------------------------------------Fields
    @PrimaryKey (autoGenerate = true)
    private int mQuestionId;

    private String mType;
    private String mDifficulty;
    private String mCategory;
    private String mQuestion;
    private String mCorrectAnswer;

    private String mIncorrectAnswer1;
    private String mIncorrectAnswer2;
    private String mIncorrectAnswer3;


    public Question(String type, String difficulty, String category, String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3) {
        mType = type;
        mDifficulty = difficulty;
        mCategory = category;
        mQuestion = question;

        mCorrectAnswer = correctAnswer;
        mIncorrectAnswer1 = incorrectAnswer1;
        mIncorrectAnswer2 = incorrectAnswer2;
        mIncorrectAnswer3 = incorrectAnswer3;
    }

    //----------------------------------------------------------------ToString
    @Override
    public String toString() {
        return "Question{" +
                "mQuestionId=" + mQuestionId +
                ", mType='" + mType + '\'' +
                ", mDifficulty='" + mDifficulty + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mQuestion='" + mQuestion + '\'' +
                ", mCorrectAnswer='" + mCorrectAnswer + '\'' +
                ", mIncorrectAnswer1='" + mIncorrectAnswer1 + '\'' +
                ", mIncorrectAnswer2='" + mIncorrectAnswer2 + '\'' +
                ", mIncorrectAnswer3='" + mIncorrectAnswer3 + '\'' +
                '}';
    }

    //-----------------------------------------------------------------Get/Set


    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
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

    public String getIncorrectAnswer1() {
        return mIncorrectAnswer1;
    }

    public void setIncorrectAnswer1(String incorrectAnswer1) {
        mIncorrectAnswer1 = incorrectAnswer1;
    }

    public String getIncorrectAnswer2() {
        return mIncorrectAnswer2;
    }

    public void setIncorrectAnswer2(String incorrectAnswer2) {
        mIncorrectAnswer2 = incorrectAnswer2;
    }

    public String getIncorrectAnswer3() {
        return mIncorrectAnswer3;
    }

    public void setIncorrectAnswer3(String incorrectAnswer3) {
        mIncorrectAnswer3 = incorrectAnswer3;
    }
}

