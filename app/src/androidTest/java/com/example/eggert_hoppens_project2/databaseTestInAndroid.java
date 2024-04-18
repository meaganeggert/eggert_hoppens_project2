package com.example.eggert_hoppens_project2;

import static junit.framework.TestCase.assertEquals;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.eggert_hoppens_project2.DB.AppDataBase;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.DB.entities.UserInfoDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

@RunWith(JUnit4.class)
public class databaseTestInAndroid {
    private AppDataBase db;
    private UserInfoDAO userDao;

    @Before
    public void createDataBase(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDataBase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userInfoDAO();
    }
    @After
    public void closeDatabase() throws IOException {
        db.close();
    }

    @Test
    public void insertAndReadData() throws Exception {
        UserInfo userInfoObject = new UserInfo("username", "password", false);
        userInfoObject.setUserId(3);

        userDao.insert(userInfoObject);
        //This is where the dao calls the normal list of UserInfo instead of LiveData
        List<UserInfo> userListFromDB = userDao.getAllRecordsList();

        assertEquals(userInfoObject, userListFromDB.get(0));
    }

}
