package com.example.eggert_hoppens_project2.DB.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserInfoDAO {

    @Query("SELECT * FROM " + AppDataBase.USER_INFO_TABLE + " WHERE mUserName == :username")
    LiveData<UserInfo> getUserByUserName(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_INFO_TABLE + " WHERE mUserId == :userId")
    LiveData<UserInfo> getUserByUserId(int userId);

    //Room library should implement these for us.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo... user);

    @Query("SELECT * FROM " + AppDataBase.USER_INFO_TABLE + " ORDER BY mUserName")
    LiveData<List<UserInfo>> getAllRecords();

    @Query("SELECT EXISTS (SELECT 1 FROM " + AppDataBase.USER_INFO_TABLE + " WHERE mUserName = :newUserName)")
    int doesContain(String newUserName);

    @Update
    void update(UserInfo... userInfos);

    @Delete
    void delete(UserInfo userInfo);

    @Query("DELETE from " + AppDataBase.USER_INFO_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + AppDataBase.USER_INFO_TABLE + " WHERE mUserName NOT IN ('admin1', 'testUser1')")
    void resetUserDB();
}
