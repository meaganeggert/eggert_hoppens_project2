package com.example.eggert_hoppens_project2.DB.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Insert
    void insertQuestions(Question... questions);

    @Update
    void updateQuestions(Question...questions);

    @Delete
    void deleteQuestion(Question question);

    @Query("SELECT * FROM " + AppDataBase.QUESTION_TABLE + " WHERE mCategory = :category")
    List<Question> getQuestionByCategory(String category);
}
