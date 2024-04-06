package com.example.eggert_hoppens_project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.Question;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Insert
    void insert(Question... questions);

    @Update
void update(Question...questions);

    @Delete
    void delete(Question question);

    @Query("SELECT * FROM " + AppDataBase.QUESTION_TABLE + " WHERE mCategory = :category")
    List<Question> getQuestionByCategory(String category);
}
