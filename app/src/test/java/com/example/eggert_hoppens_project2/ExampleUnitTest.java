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
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}