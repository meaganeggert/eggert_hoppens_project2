package com.example.eggert_hoppens_project2;

import static junit.framework.TestCase.assertEquals;

import com.example.eggert_hoppens_project2.DB.AppDataBase;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.DB.entities.UserInfoDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.room.Room;
import androidx.test.core.*;

import java.io.IOException;
import java.util.List;

@RunWith(JUnit4.class)
public class databaseTest {
    private AppDataBase db;
    private UserInfoDAO userDao;

    @Before
    public void createDatabase() {
//        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDataBase.class)
//                .allowMainThreadQueries()
//                .build();
//        userDao = db.userInfoDAO();
    }

    @After
    public void closeDatabase() throws IOException {
        db.close();
    }

    @Test
    public void insertAndReadData() throws Exception {
        UserInfo userInfo = new UserInfo("username", "password", false);
        userDao.insert(userInfo);
        List<UserInfo> users = userDao.getAllRecords().getValue();
        assertEquals(userInfo, users.get(0));
    }

}
