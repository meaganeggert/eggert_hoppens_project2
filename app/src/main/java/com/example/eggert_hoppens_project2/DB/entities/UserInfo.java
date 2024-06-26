package com.example.eggert_hoppens_project2.DB.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eggert_hoppens_project2.DB.AppDataBase;

import java.util.Objects;

@Entity (tableName = AppDataBase.USER_INFO_TABLE)
public class UserInfo {
    //-------------------------------------------------------------------------------Fields
    @PrimaryKey (autoGenerate = true)
    private int mUserId;

    private String mUserName;

    private String mUserPassword;

    private boolean mIsAdmin = false;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return mUserId == userInfo.mUserId && mIsAdmin == userInfo.mIsAdmin && Objects.equals(mUserName, userInfo.mUserName) && Objects.equals(mUserPassword, userInfo.mUserPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserId, mUserName, mUserPassword, mIsAdmin);
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
