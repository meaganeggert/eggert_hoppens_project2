package com.example.eggert_hoppens_project2.DB;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.QuestionDAO;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.ScoreDAO;
import com.example.eggert_hoppens_project2.DB.entities.UserInfoDAO;
import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserInfo.class, Question.class, Score.class}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "MAINDATABASE";
    public static final String USER_INFO_TABLE = "USERTABLE";
    public static final String SCORE_TABLE = "SCORETABLE";
    public static final String QUESTION_TABLE = "QUESTTABLE";

    private static volatile AppDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDataBase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public  void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created!");
            //TODO : add databaseWriteExecutor.execute(() -> {}
            databaseWriteExecutor.execute(() -> {
                UserInfoDAO dao = INSTANCE.userInfoDAO();
                QuestionDAO questionDAO = INSTANCE.questionDAO();
                ScoreDAO scoreDAO = INSTANCE.scoreDAO();

                dao.resetUserDB();
                questionDAO.deleteAllQuestions();

                UserInfo admin = new UserInfo("admin1", "admin1", true);
                dao.insert(admin);

                UserInfo testUser = new UserInfo("testUser1", "testUser1", false);
                dao.insert(testUser);

                Question testQuestion1 = new Question(
                        "multiple",
                        "medium",
                        "Video Games",
                        "Which of these is the name of a city in the Grand Theft Auto series?",
                        "San Andreas",
                        "Yabba",
                        "Dabba",
                        "Doo"
                );
                questionDAO.insertQuestion(testQuestion1);

                Question testQuestion2 = new Question(
                        "multiple",
                        "medium",
                        "Video Games",
                        "How many fingers do humans have?",
                        "Ten",
                        "Five",
                        "Nine",
                        "Seven"
                );
                questionDAO.insertQuestion(testQuestion2);
            });
        }
    };

    public abstract UserInfoDAO userInfoDAO();

    public abstract QuestionDAO questionDAO();

    public abstract ScoreDAO scoreDAO();
}
