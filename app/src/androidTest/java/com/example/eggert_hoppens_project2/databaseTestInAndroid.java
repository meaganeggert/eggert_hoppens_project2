package com.example.eggert_hoppens_project2;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.eggert_hoppens_project2.DB.AppDataBase;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.QuestionDAO;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.ScoreDAO;
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
    private QuestionDAO questionDAO;
    private ScoreDAO scoreDAO;

    @Before
    public void createDataBase(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDataBase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userInfoDAO();
        questionDAO = db.questionDAO();
        scoreDAO = db.scoreDAO();
    }
    @After
    public void closeDatabase() throws IOException {
        db.close();
    }

    //_________________________________________________________________UserInfo Tests________________
    @Test
    public void insertAndReadData() throws Exception {
        UserInfo userInfoObject = new UserInfo("username", "password", false);
        userInfoObject.setUserId(1);

        userDao.insert(userInfoObject);
        //This is where the dao calls the normal list of UserInfo instead of LiveData
        List<UserInfo> userListFromDB = userDao.getAllRecordsList();

        assertEquals(userInfoObject, userListFromDB.get(0));
    }

    @Test
    public void userIdExist() throws Exception {
        UserInfo userInfoObject = new UserInfo("username", "password", false);
        userInfoObject.setUserId(1);

        userDao.insert(userInfoObject);
        assertTrue(userDao.doesContainUserId(1));
    }

    @Test
    public void deleteUserById() throws Exception {
        UserInfo userInfoObject = new UserInfo("username", "password", false);
        userInfoObject.setUserId(1);

        userDao.insert(userInfoObject);
        assertTrue(userDao.doesContainUserId(1));

        userDao.deleteUserById(1);
        assertFalse(userDao.doesContainUserId(1));
    }

    //________________________________________________________________________Question Tests________
    @Test
    public void insertQuestionAndReadData() throws Exception {
        Question questionObject = new Question(
                "multiple",
                "medium",
                "Video Games",
                "Which of these is the name of a city in the Grand Theft Auto series?",
                "San Andreas",
                "Yabba",
                "Dabba",
                "Doo"
        );
        questionObject.setQuestionId(1);
        questionDAO.insertQuestion(questionObject);
        assertEquals(questionObject, questionDAO.getQuestObjectById(1));
    }

    @Test
    public void questionIdExist() throws Exception {
        Question questionObject = new Question(
                "multiple",
                "medium",
                "Video Games",
                "Which of these is the name of a city in the Grand Theft Auto series?",
                "San Andreas",
                "Yabba",
                "Dabba",
                "Doo"
        );
        questionObject.setQuestionId(1);
        questionDAO.insertQuestion(questionObject);
        assertTrue(questionDAO.doesContainQuestionId(1));
    }

    @Test
    public void deleteQuestionById() throws Exception {
        Question questionObject = new Question(
                "multiple",
                "medium",
                "Video Games",
                "Which of these is the name of a city in the Grand Theft Auto series?",
                "San Andreas",
                "Yabba",
                "Dabba",
                "Doo"
        );
        questionObject.setQuestionId(1);
        questionDAO.insertQuestion(questionObject);
        assertTrue(questionDAO.doesContainQuestionId(1));

        questionDAO.deleteById(1);
        assertFalse(questionDAO.doesContainQuestionId(1));
    }

    //_____________________________________________________________________________Score Tests______
    @Test
    public void insertScoreAndReadData() throws Exception {
        Score scoreObject = new Score(3, "Harry", 300, 0, 5, 9.0);
        scoreObject.setScoreId(1);

        scoreDAO.insertScore(scoreObject);

        assertEquals(scoreObject, scoreDAO.getScoreByScoreIdNormalList(1));
    }

    @Test
    public void scoreExistByUserId() throws Exception {
        Score scoreObject = new Score(3, "Harry", 300, 0, 5, 9.0);
        scoreObject.setScoreId(1);

        scoreDAO.insertScore(scoreObject);

        assertTrue(scoreDAO.doesContainScoreById(3));
    }

    @Test
    public void deleteAllScores() throws Exception {
        Score scoreObject = new Score(3, "Harry", 300, 0, 5, 9.0);
        scoreObject.setScoreId(1);

        scoreDAO.insertScore(scoreObject);
        assertTrue(scoreDAO.doesScoreExist());

        scoreDAO.deleteAllScores();
        assertFalse(scoreDAO.doesScoreExist());
    }

}
