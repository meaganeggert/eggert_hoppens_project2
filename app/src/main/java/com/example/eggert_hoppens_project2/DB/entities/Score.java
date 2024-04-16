package com.example.eggert_hoppens_project2.DB.entities;


import androidx.room.Entity;

import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;


//ParentColumn is the UserInfo.class, ChildColumn is the Score.class

//, foreignKeys = {
//        @ForeignKey(entity = UserInfo.class, parentColumns = "mUserId", childColumns = "mUserId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
//        @ForeignKey(entity = UserInfo.class, parentColumns = "mUserName", childColumns = "mUserName", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
//        //@ForeignKey(entity = GameMode.class, parentColumns = "mGameModeId", childColumns = "mGameModeId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
//        //Last foreignKey
//})

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
