package com.example.eggert_hoppens_project2.DB;

import android.app.Application;
import android.util.Log;

import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.SignUpActivity;
import com.example.eggert_hoppens_project2.UserInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private UserInfoDAO userinfoDAO;
    private ArrayList<UserInfo> allInfo;

    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        this.userinfoDAO = db.userInfoDAO();
        this.allInfo = (ArrayList<UserInfo>) this.userinfoDAO.getAllRecords();
    }

    public static AppRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<AppRepository> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(SignUpActivity.TAG, "Problem getting AppRepository, thread error");
        }
        return null;
    }

    public ArrayList<UserInfo> getAllInfo() {
        Future<ArrayList<UserInfo>> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<UserInfo>>() {
                    @Override
                    public ArrayList<UserInfo> call() throws Exception {
                        return (ArrayList<UserInfo>) userinfoDAO.getAllRecords();
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
        AppDataBase.databaseWriteExecutor.execute(() ->
        {
            userinfoDAO.insert(user);
        });
    }

    public boolean containsUserName(String newUser) {
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        int temp = userinfoDAO.doesContain(newUser);
                        if (temp >= 1) return true;
                        else return false;
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when comparing new user to existing users");
        }
        return true;
    }

}
