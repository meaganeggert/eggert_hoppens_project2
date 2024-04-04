package com.example.eggert_hoppens_project2.DB;

import android.app.Application;
import android.util.Log;

import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.UserInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private UserInfoDAO userinfoDAO;
    private ArrayList<UserInfo> allInfo;

    public AppRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        this.userinfoDAO = db.userInfoDAO();
        this.allInfo = this.userinfoDAO.getAllRecords();
    }

    public ArrayList<UserInfo> getAllInfo() {
        Future<ArrayList<UserInfo>> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<UserInfo>>() {
                    @Override
                    public ArrayList<UserInfo> call() throws Exception {
                        return userinfoDAO.getAllRecords();
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all user info in the repository");
        }
        return null;
    }

    public void insertUserInfo(UserInfo user) {
        AppDataBase.databaseWriteExecutor.execute(()->
        {
            userinfoDAO.insert(user);
        });
    }

    }
