package com.example.eggert_hoppens_project2.gamemodes;


import androidx.annotation.NonNull;

public class GameModeSettings {
    //________________________________________________________________________________________Fields
    private String gameModeName;
    private String gameModeDescription = "";
    private String categoryName = "";

    private boolean hasStrikes = false;
    private boolean isTimed = false;
    private double timeStart = 0.0;
    private double timeEnd = 0.0;
    private boolean timerIsRunning = false;

    private int dbTotalQuestions = 0;
    private int currentQuestionIndex = 0;

    //User gameplay info
    private int userScore = 0;
    private int userStrikes = 0;

    //___________________________________________________________________________________Constructor
    public GameModeSettings(String gameModeExtra) {
        this.gameModeName = gameModeExtra;
        this.userScore = 0;
        setGameMode();
    }
    //_______________________________________________________________________________GameModeMethods
    private void setGameMode(){
        switch (gameModeName) {
            case "Zen":
                this.gameModeDescription = "Un-timed quiz, score will not be recorded.";
                break;
            case "Speed":
                this.gameModeDescription = "Timed quiz, score will not be recorded.";
                this.isTimed = true;
                setTimeStart();
                break;
            case "Compete":
                this.gameModeDescription = "Timed quiz, score will be recorded and posted on the scoreboard.";
                this.isTimed = true;
                this.hasStrikes = true;
                setTimeStart();
                break;
            default:
                this.gameModeDescription = "Passed game mode has not been established, please try again.";
                break;
        }
    }

    public void incrementScore(){
        this.userScore++;
    }

    public void incrementStrikes(){
        if(this.hasStrikes){
            this.userStrikes++;
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
            totalTime = totalTime / 1000;
        }
        return totalTime;
    }

    public boolean checkGameEnds(){
        if(currentQuestionIndex >= dbTotalQuestions - 1){
            return true;
        }
        if(this.gameModeName.equals("Compete")){
            if(this.userStrikes >= 3){
                return true;
            }
        }
        return false;
    }

    public void endGame(){
        if(this.isTimed){
            setTimeEnd();
        }
    }
    //______________________________________________________________________________GeneratedMethods
    @NonNull
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

    public boolean isHasStrikes() {
        return hasStrikes;
    }

    public void setHasStrikes(boolean hasStrikes) {
        this.hasStrikes = hasStrikes;
    }

    public double getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(double timeStart) {
        this.timeStart = timeStart;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(double timeEnd) {
        this.timeEnd = timeEnd;
    }

    public boolean isTimerIsRunning() {
        return timerIsRunning;
    }

    public void setTimerIsRunning(boolean timerIsRunning) {
        this.timerIsRunning = timerIsRunning;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserStrikes() {
        return userStrikes;
    }

    public void setUserStrikes(int userStrikes) {
        this.userStrikes = userStrikes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getDbTotalQuestions() {
        return dbTotalQuestions;
    }

    public void setDbTotalQuestions(int dbTotalQuestions) {
        this.dbTotalQuestions = dbTotalQuestions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }
}
