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
public interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question... question);

    @Update
    void updateQuestions(Question...questions);

    @Delete
    void deleteQuestion(Question question);

    @Query("DELETE FROM " + AppDataBase.QUESTION_TABLE)
    void deleteAllQuestions();

    @Query("SELECT * FROM " + AppDataBase.QUESTION_TABLE + " WHERE mCategory = :category")
    LiveData<List<Question>> getAllQuestionsByCategory(String category);

    @Query("SELECT * FROM " + AppDataBase.QUESTION_TABLE)
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM " + AppDataBase.QUESTION_TABLE + " WHERE mQuestionId = :questionId")
    LiveData<Question> getQuestionById(int questionId);

    @Query("SELECT EXISTS(SELECT * FROM " + AppDataBase.QUESTION_TABLE + " WHERE mCategory = :category)")
    boolean doesContainCategory(String category);

    @Query("DELETE FROM " + AppDataBase.QUESTION_TABLE + " WHERE mQuestionId = :questionId")
    void deleteById(int questionId);
}
