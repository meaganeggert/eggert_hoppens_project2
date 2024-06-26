package com.example.eggert_hoppens_project2.DB;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.CategoryActivity;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.QuestionDAO;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.ScoreDAO;
import com.example.eggert_hoppens_project2.DB.entities.UserInfoDAO;
import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.PlayResultsActivity;
import com.example.eggert_hoppens_project2.SignUpActivity;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private UserInfoDAO userinfoDAO;

    private QuestionDAO questionDAO;

    private ScoreDAO scoreDAO;

    private LiveData<List<UserInfo>> allInfo;
    private LiveData<List<Question>> allQuestionInfo;
    private LiveData<List<Score>> allScores;

    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        this.userinfoDAO = db.userInfoDAO();
        this.allInfo = this.userinfoDAO.getAllRecords();

        this.questionDAO = db.questionDAO();
        this.allQuestionInfo = this.questionDAO.getAllQuestions();

        this.scoreDAO = db.scoreDAO();
        this.allScores = this.scoreDAO.getScoresByHighest();

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

    public LiveData<UserInfo> getUserByUserName(String username) {
        return userinfoDAO.getUserByUserName(username);
    }

    public LiveData<UserInfo> getUserByUserId(int userId) {
        return userinfoDAO.getUserByUserId(userId);
    }

    public LiveData<List<UserInfo>> getAllInfo() {
        return userinfoDAO.getAllRecords();
    }

    public void insertUserInfo(UserInfo user) {
        AppDataBase.databaseWriteExecutor.execute(() ->
        {
            userinfoDAO.insert(user);
        });
    }

    public void clearUsers() {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            userinfoDAO.resetUserDB();
        });
    }

    public void updateUserName(String newUserName, int userId) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            userinfoDAO.updateUserName(newUserName, userId);
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

    public void updatePassword(String newPassword, int userId) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            userinfoDAO.updatePassword(newPassword, userId);
        });
    }



    public LiveData<Question> getQuestionById(int questionId){
        return questionDAO.getQuestionById(questionId);
    }

    public Question getQuestionObjById(int questionId) {
        return questionDAO.getQuestObjectById(questionId);
    }

    public LiveData<List<Question>> getAllQuestions(){
        return questionDAO.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestionsByCategory(String category){
        return questionDAO.getAllQuestionsByCategory(category);
    }

    public boolean doesContainCategory(String category){
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean temp = questionDAO.doesContainCategory(category);
                return temp;
            }
        });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(CategoryActivity.TAG, "Problem when getting question by category.");
        }
        return false;
    }

    public boolean doesContainQuestionId(int questionId) {
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean temp = questionDAO.doesContainQuestionId(questionId);
                return temp;
            }
        });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(CategoryActivity.TAG, "Problem when getting question by ID.");
        }
        return false;
    }

    public void insertQuestion(Question question){
        AppDataBase.databaseWriteExecutor.execute(() ->
        {
            questionDAO.insertQuestion(question);
        });
    }

    public void deleteQuestionById(int questionId) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            questionDAO.deleteById(questionId);
        });
    }


    //--------Score Handling---------//
    public boolean doesScoreExist(){
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean temp = scoreDAO.doesScoreExist();
                return temp;
            }
        });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(CategoryActivity.TAG, "Problem when getting score list.");
        }
        return false;
    }

    public boolean userScoreAlreadyExist(int userId){
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean temp = scoreDAO.doesContainScoreById(userId);
                return temp;
            }
        });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(PlayResultsActivity.TAG, "Problem getting score by user ID.");
        }
        return false;
    }

    public void updateUserScoreInfo(Score score){
        scoreDAO.updateScore(score);
    }

    public LiveData<Score> getScoreByScoreId(int scoreId) {
        return scoreDAO.getScoreByScoreId(scoreId);
    }

    public LiveData<List<Score>> getScoreListByUserId(int userId) {
        return scoreDAO.getScoreListByUserId(userId);
    }

    public LiveData<Score> getScoreByUserId(int userId){
        return scoreDAO.getScoreByUserId(userId);
    }

    public LiveData<List<Score>> getAllScores() {
        return scoreDAO.getScoresByHighest();
    }

    public void insertScore(Score score){
        AppDataBase.databaseWriteExecutor.execute(() ->
        {
            scoreDAO.insertScore(score);
        });
    }


    public void clearScoreboard() {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            userinfoDAO.resetScoreboard();
        });
    }


    public void deleteUserById(int mUserId) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            userinfoDAO.deleteUserById(mUserId);
        });
    }

    public boolean doesContainUserId(int mUserId) {
        Future<Boolean> future = AppDataBase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean temp = userinfoDAO.doesContainUserId(mUserId);
                return temp;
            }
        });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(CategoryActivity.TAG, "Problem when getting user by ID.");
        }
        return false;
    }
}
