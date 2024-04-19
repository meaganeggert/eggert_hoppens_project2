package com.example.eggert_hoppens_project2;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.eggert_hoppens_project2.gamemodes.GameModeSettings;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String[] gameModeNames = {"Zen", "Speed", "Compete"};
    GameModeSettings gameModeController;

    @Test
    public void gameModeSetsCorrectly(){
        //Pass in "Zen" and set game mode accordingly. Should have no timer and no strikes.
        gameModeController = new GameModeSettings(gameModeNames[0]);
        assertFalse(gameModeController.isTimed());
        assertFalse(gameModeController.isHasStrikes());
        gameModeController.endGame();
        gameModeController = null;

        //Pass in "Speed" and set game mode accordingly. Should have a timer and no strikes.
        gameModeController = new GameModeSettings(gameModeNames[1]);
        assertTrue(gameModeController.isTimed());
        assertFalse(gameModeController.isHasStrikes());
        gameModeController.endGame();
        gameModeController = null;

        //Pass in Compete and set game mode accordingly. Should have a timer and strikes.
        gameModeController = new GameModeSettings(gameModeNames[2]);
        assertTrue(gameModeController.isTimed());
        assertTrue(gameModeController.isHasStrikes());
        gameModeController.endGame();
        gameModeController = null;
    }

    @Test
    public void gameModeTimerWorks(){
        int index = 0;
        //Upon passing "Speed" or "Compete", setTimerStart() should automatically be called.
        gameModeController = new GameModeSettings(gameModeNames[1]);
        //For the sake of the test, we cannot have the timer stop before the system even counts to 0.001.
        //So, while the time is lower than 1 second, increment the index.
        while(gameModeController.getTotalTime() < 1.0){
            index++;
        }
        //Once the total time gets to 1 second, end the timer.
        gameModeController.setTimeEnd();
        //Obviously, the index will be higher than 0, thus the timer works.
        assertNotEquals(0, index);
        gameModeController.endGame();
    }

    @Test
    public void gameEndWorks() {
        boolean tester = false;
        // Set to compete
        gameModeController = new GameModeSettings(gameModeNames[2]);

        // If there are still more questions..
        gameModeController.setDbTotalQuestions(5);
        gameModeController.setCurrentQuestionIndex(0);
        tester = gameModeController.checkGameEnds();
        assertFalse(tester); // Game shouldn't be over

        // If we have reached the last question
        gameModeController.setDbTotalQuestions(5);
        gameModeController.setCurrentQuestionIndex(5);
        tester = gameModeController.checkGameEnds();
        assertTrue(tester); // Game should be over

        // If game mode is 'Compete'
        String gameModeName = gameModeController.getGameModeName();
        assertEquals(gameModeName, "Compete");
        gameModeController.setDbTotalQuestions(5);
        gameModeController.setCurrentQuestionIndex(0);
        gameModeController.setUserStrikes(0);
        tester = gameModeController.checkGameEnds();
        assertFalse(tester); // There are more questions, and the user has more strikes

        gameModeController.setUserStrikes(3); // 3 Strikes
        tester = gameModeController.checkGameEnds();
        assertTrue(tester); // User struck out
    }

    @Test
    public void testAddStrike() {
        // If game mode isn't 'Compete'
        gameModeController = new GameModeSettings(gameModeNames[0]);
        int userStrikes = gameModeController.getUserStrikes(); // 0
        gameModeController.incrementStrikes(); // Shouldn't add anything
        assertEquals(userStrikes, 0); // Still 0

        // If game mode IS 'Compete'
        gameModeController = new GameModeSettings(gameModeNames[2]);
        userStrikes = gameModeController.getUserStrikes(); // 0
        assertEquals(userStrikes, 0);
        gameModeController.incrementStrikes(); // Should add 1 strike
        userStrikes = gameModeController.getUserStrikes();
        assertEquals(userStrikes, 1);
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}