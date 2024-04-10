package com.example.eggert_hoppens_project2.DB;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.eggert_hoppens_project2.DB.entities.UserInfoDAO;
import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserInfo.class}, version = 1, exportSchema = false)
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
                dao.deleteAll();
                UserInfo admin = new UserInfo("admin1", "admin1", true);
                dao.insert(admin);

                UserInfo testUser = new UserInfo("testUser1", "testUser1", false);
                dao.insert(testUser);
            });
        }
    };

    public abstract UserInfoDAO userInfoDAO();
}
