package com.example.eggert_hoppens_project2;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

//ParentColumn is the UserInfo.class, ChildColumn is the Score.class
@Entity(tableName = AppDataBase.SCORE_TABLE, foreignKeys = {
        @ForeignKey(entity = UserInfo.class, parentColumns = "mUserId", childColumns = "mUserId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = UserInfo.class, parentColumns = "mUserName", childColumns = "mUserName", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
        //@ForeignKey(entity = GameMode.class, parentColumns = "mGameModeId", childColumns = "mGameModeId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
        //Last foreignKey
})
public class Score {
    //--------------------------------------------------------Fields
    @PrimaryKey (autoGenerate = true)
    private int mScoreId;

    private int mUserId;
    private int mUserName;
    private int mGameModeId;

    private int mScore;

    //---------------------------------------------------------Constructor
    public Score(int scoreId, int userId, int gameModeId) {
        mScoreId = scoreId;
        mUserId = userId;
        mGameModeId = gameModeId;
    }

    //-----------------------------------------------------------toString
    @Override
    public String toString() {
        return "Score{" +
                "mScoreId=" + mScoreId +
                ", mUserId=" + mUserId +
                ", mUserName=" + mUserName +
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
