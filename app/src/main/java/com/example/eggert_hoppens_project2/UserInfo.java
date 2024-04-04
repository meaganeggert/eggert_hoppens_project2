package com.example.eggert_hoppens_project2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {
    //-------------------------------------------------------------------------------Fields
    @PrimaryKey
    @NonNull
    private String mUserName;
    private String mUserPassword;

    //mUserId will be the primary key, first have to set up AppDataBase;
    private int mUserId;
    private boolean mIsAdmin;

    //-------------------------------------------------------------------------------Constructor
    public UserInfo(String userName, String userPassword, boolean isAdmin) {
        mUserName = userName;
        mUserPassword = userPassword;
        mIsAdmin = isAdmin;
    }

    //--------------------------------------------------------------------------------ToString
    @Override
    public String toString() {
        return "UserInfo{" +
                "UserName='" + mUserName + '\'' +
                ", UserPassword='" + mUserPassword + '\'' +
                ", UserId=" + mUserId +
                ", IsAdmin=" + mIsAdmin +
                '}';
    }

    //---------------------------------------------------------------------------------Set/Get
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        mUserPassword = userPassword;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }
}
