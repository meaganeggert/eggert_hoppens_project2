package com.example.eggert_hoppens_project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.UserInfo;

@Dao
public interface UserInfoDAO {
    //Room library should implement these for us.
    @Insert
    void insert(UserInfo... userInfos);

    @Update
    void update(UserInfo... userInfos);

    @Delete
    void delete(UserInfo userInfo);
}
