package com.example.eggert_hoppens_project2.DB.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.Objects;

@Entity(tableName = AppDataBase.SCORE_TABLE)
public class Score {
    //--------------------------------------------------------Fields
    @PrimaryKey (autoGenerate = true)
    private int mScoreId;
    private int mUserId;
    private String mUserName;
    private int mUserScore;
    private int mUserStrikes;
    private int mTotalQuestions;
    private double mTime;

    //---------------------------------------------------------Constructor
    public Score(int userId, String userName, int userScore, int userStrikes, int totalQuestions, double time) {
        mUserId = userId;
        mUserName = userName;
        mUserScore = userScore;
        mUserStrikes = userStrikes;
        mTotalQuestions = totalQuestions;
        mTime = time;
    }

    //-----------------------------------------------------------toString
    @Override
    public String toString() {
        return  mUserName +
                "\tStrikes=" + mUserStrikes +
                "\tScore=" + mUserScore + "/" + mTotalQuestions +
                "\tTime=" + mTime + " seconds\n" +
                "========================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return mScoreId == score.mScoreId && mUserId == score.mUserId && mUserScore == score.mUserScore && mUserStrikes == score.mUserStrikes && mTotalQuestions == score.mTotalQuestions && Double.compare(mTime, score.mTime) == 0 && Objects.equals(mUserName, score.mUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mScoreId, mUserId, mUserName, mUserScore, mUserStrikes, mTotalQuestions, mTime);
    }

    //-----------------------------------------------------------Set/Get
    public int getScoreId() {
        return mScoreId;
    }

    public void setScoreId(int scoreId) {
        mScoreId = scoreId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public int getUserScore() {
        return mUserScore;
    }

    public void setUserScore(int userScore) {
        mUserScore = userScore;
    }

    public int getUserStrikes() {
        return mUserStrikes;
    }

    public void setUserStrikes(int userStrikes) {
        mUserStrikes = userStrikes;
    }

    public int getTotalQuestions() {
        return mTotalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        mTotalQuestions = totalQuestions;
    }

    public double getTime() {
        return mTime;
    }

    public void setTime(double time) {
        mTime = time;
    }
}
