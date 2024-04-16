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
    private int mUserName;
    private int mGameModeId;

    private int mScore;

    //---------------------------------------------------------Constructor
    @Ignore
    public Score(int scoreId, int userId, int gameModeId) {
        mScoreId = scoreId;
        mUserId = userId;
        mGameModeId = gameModeId;
    }

    public Score(int mScoreId, int mUserId, int mUserName, int mGameModeId, int mScore) {
        this.mScoreId = mScoreId;
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mGameModeId = mGameModeId;
        this.mScore = mScore;
    }

    //-----------------------------------------------------------toString
    @Override
    public String toString() {
        return "Score{" +
                "mScoreId=" + mScoreId +
                ", mUserId=" + mUserId +
                ", mGameModeId=" + mGameModeId +
                ", mScore=" + mScore +
                '}';

        //return mUserName + "\t\t" + mScore + "\n";
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

    public int getUserName() {
        return mUserName;
    }

    public void setUserName(int userName) {
        mUserName = userName;
    }

    public int getGameModeId() {
        return mGameModeId;
    }

    public void setGameModeId(int gameModeId) {
        mGameModeId = gameModeId;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
