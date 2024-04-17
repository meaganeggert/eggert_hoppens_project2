package com.example.eggert_hoppens_project2.DB.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);

    @Update
    void updateScore(Score... score);

    @Delete
    void deleteScore(Score score);


    @Query("SELECT EXISTS(SELECT 1 FROM " + AppDataBase.SCORE_TABLE + " WHERE mUserId = :userId)")
    boolean doesContainScoreById(int userId);

    @Query("SELECT EXISTS(SELECT * FROM " + AppDataBase.SCORE_TABLE + ")")
    boolean doesScoreExist();

//    @Query("UPDATE " + AppDataBase.SCORE_TABLE + " Set mScoreId = :scoreId")
//    LiveData<Score> updateUserScore(int scoreId);

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " ORDER BY mUserScore DESC")
    LiveData<List<Score>> getScoresByHighest();

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " WHERE mUserId = :userId")
    LiveData<List<Score>> getScoreByUserId(int userId);

    @Query("DELETE from " + AppDataBase.SCORE_TABLE)
    void deleteAllScores();

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " WHERE mScoreId = :scoreId")
    LiveData<Score> getScoreByScoreId(int scoreId);
}
