package com.example.eggert_hoppens_project2.DB;

import androidx.room.Database;

import com.example.eggert_hoppens_project2.UserInfo;

@Database(entities = {UserInfo.class}, version = 1)
public class AppDataBase {
    public static final String DATABASE_NAME = "UserInfo.db";
    public static final String USER_INFO_TABLE = "userInfo_table";
}
