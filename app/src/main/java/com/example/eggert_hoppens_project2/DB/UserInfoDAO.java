package com.example.eggert_hoppens_project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eggert_hoppens_project2.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserInfoDAO {
    //Room library should implement these for us.
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo user);

    @Query ("Select * from " + AppDataBase.USER_INFO_TABLE)
    ArrayList<UserInfo> getAllRecords();

    @Update
    void update(UserInfo... userInfos);

    @Delete
    void delete(UserInfo userInfo);
}
