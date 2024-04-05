package com.example.eggert_hoppens_project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.Score;

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
    List<Score> getScoresByHighest();

    @Query("SELECT * FROM " + AppDataBase.SCORE_TABLE + " WHERE mUserId = ")
}
