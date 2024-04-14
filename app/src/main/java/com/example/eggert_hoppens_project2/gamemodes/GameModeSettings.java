package com.example.eggert_hoppens_project2.gamemodes;

import java.util.Objects;

public abstract class GameModeSettings {
    //________________________________________________________________________________________Fields
    private String gameModeName = "";
    private String gameModeDescription = "";
    private boolean isTimed = false;

    private double timeStart = 0.0;
    private double timeEnd = 0.0;
    private boolean timerIsRunning = false;

    //___________________________________________________________________________________Constructor
    public GameModeSettings(String gameModeExtra) {
        this.gameModeName = gameModeName;
        setGameMode();
    }
    //_______________________________________________________________________________GameModeMethods
    private void setGameMode(){
        switch (gameModeName) {
            case "Zen":
                this.gameModeDescription = "Un-timed quiz, score will not be recorded.";
                break;
            case "Timed":
                this.gameModeDescription = "Timed quiz, score will not be recorded.";
                this.isTimed = true;
                break;
            case "Compete":
                this.gameModeDescription = "Timed quiz, score will be recorded and posted on the scoreboard.";
                this.isTimed = true;
                break;
            default:
                this.gameModeDescription = "Passed game mode has not been established, please try again.";
                break;
        }
    }

    public void setTimeStart(){
        if(this.isTimed){
            this.timeStart = System.currentTimeMillis();
            this.timerIsRunning = true;
        }
    }
    public void setTimeEnd(){
        if(this.isTimed){
            this.timeEnd = System.currentTimeMillis();
            this.timerIsRunning = false;
        }
    }

    public double getTotalTime(){
        double totalTime = 0.0;
        if(this.isTimed){
            if(timerIsRunning){
                totalTime = System.currentTimeMillis() - this.timeStart;
            } else{
                totalTime = this.timeEnd - this.timeStart;
            }
        }
        return totalTime;
    }

    //______________________________________________________________________________GeneratedMethods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameModeSettings that = (GameModeSettings) o;
        return isTimed == that.isTimed && Objects.equals(gameModeName, that.gameModeName) && Objects.equals(gameModeDescription, that.gameModeDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameModeName, gameModeDescription, isTimed);
    }

    @Override
    public String toString() {
        return "GameModeSettings{" +
                "gameModeName='" + gameModeName + '\'' +
                ", gameModeDescription='" + gameModeDescription + '\'' +
                ", isTimed=" + isTimed +
                '}';
    }

    public String getGameModeName() {
        return gameModeName;
    }

    public void setGameModeName(String gameModeName) {
        this.gameModeName = gameModeName;
    }

    public String getGameModeDescription() {
        return gameModeDescription;
    }

    public void setGameModeDescription(String gameModeDescription) {
        this.gameModeDescription = gameModeDescription;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        this.isTimed = timed;
    }
}
