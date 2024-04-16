package com.example.eggert_hoppens_project2.DB.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Insert
    void insertScore(Score... scores);

    @Update
    void updateScore(Score... scores);

    @Delete
    void deleteScore(Score score);

    //Retrieve scores by highest score.
    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " ORDER BY mScore DESC")
    LiveData<List<Score>> getScoresByHighest();

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " WHERE mUserId = :userId")
    LiveData<List<Score>> getScoreByUserId(int userId);

    @Query("DELETE from " + AppDataBase.SCORE_TABLE)
    void deleteAllScores();

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " WHERE mScoreId = :scoreId")
    LiveData<Score> getScoreByScoreId(int scoreId);
}
