package com.example.eggert_hoppens_project2.DB.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.Objects;

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

    //----------------------------------------------------------------Constructors

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

    //__________________________________________________________________Hash/Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return mQuestionId == question.mQuestionId && Objects.equals(mType, question.mType) && Objects.equals(mDifficulty, question.mDifficulty) && Objects.equals(mCategory, question.mCategory) && Objects.equals(mQuestion, question.mQuestion) && Objects.equals(mCorrectAnswer, question.mCorrectAnswer) && Objects.equals(mIncorrectAnswer1, question.mIncorrectAnswer1) && Objects.equals(mIncorrectAnswer2, question.mIncorrectAnswer2) && Objects.equals(mIncorrectAnswer3, question.mIncorrectAnswer3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mQuestionId, mType, mDifficulty, mCategory, mQuestion, mCorrectAnswer, mIncorrectAnswer1, mIncorrectAnswer2, mIncorrectAnswer3);
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

