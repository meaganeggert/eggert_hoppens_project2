package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityPlayResultsScreenBinding;

import java.util.Locale;

public class PlayResultsActivity extends AppCompatActivity {
    private AppRepository repository;
    ActivityPlayResultsScreenBinding binding;
    public static final String TAG = "EGGHOP_Play_Results";

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    private static final String USER_SCORE_NAME = "User Score";
    private static final String USER_STRIKE_NAME = "User Strikes";
    private static final String TOTAL_QUESTIONS_ANSWERED_NAME = "Total Answered Questions";
    private static final String USER_TIME_NAME = "User Time";
    private static final String GAME_MODE_NAME = "Game Mode Name";  //To be used for scoreboard database.

    private static int userScore = 0;
    private static int userStrikes = 0;
    private static int totalAnsweredQuestions = 0;
    private static double userTime = 0.0;
    private static String gameModeName = "";

    private static Score currentUserScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayResultsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        checkLoggedInUser();

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        if(getIntent().hasExtra(TOTAL_QUESTIONS_ANSWERED_NAME)){
            totalAnsweredQuestions = getIntent().getIntExtra(TOTAL_QUESTIONS_ANSWERED_NAME, 0);
        }
        if(getIntent().hasExtra(USER_SCORE_NAME)){
            userScore = getIntent().getIntExtra(USER_SCORE_NAME, 0);
            binding.resultScoreTextView.setText(String.format(Locale.US, "Score: %d" + "/" + "%d", userScore, totalAnsweredQuestions));
        }
        if(getIntent().hasExtra(USER_STRIKE_NAME)){
            userStrikes = getIntent().getIntExtra(USER_STRIKE_NAME, 0);
            binding.resultStrikesTextView.setText(String.format(Locale.US, "Strikes: %d", userStrikes));
        }
        if(getIntent().hasExtra(USER_TIME_NAME)){
            userTime = getIntent().getDoubleExtra(USER_TIME_NAME, 0);
            binding.resultTimeTextView.setText(String.format(Locale.US, "Time: %.2f seconds", userTime));
        }
        //In case we need it for the scoreboard database.
        if(getIntent().hasExtra(GAME_MODE_NAME)){
            gameModeName = getIntent().getStringExtra(GAME_MODE_NAME);
        }



        //Only record score info if the gameMode played was Compete mode;
        if(gameModeName.equals("Compete")){
            currentUserScore = new Score(loggedInUserId, loggedInUser, userScore, userStrikes, totalAnsweredQuestions, userTime);


            //Here is where the score data will be compared.
            //If the user has a previous score, set the currentUserScore's id to the previous one.
            if(checkUserScoreExists(loggedInUserId)){
                updateScore(loggedInUserId);
            }
            else{  //If user doesn't already have a score, just insert this one.
                insertUserScore(currentUserScore);
            }
            //insertUserScore(currentUserScore);  //FOR NOW IT WILL JUST KEEP INSERTING SCORES, EVEN IF THERE ALREADY IS ONE WITH THE SAME USERID.
        }

        //Not entirely sure if this works the way I want it to. Supposed to clear all the activities leading up to this
        //and go back to the landing page.
        binding.mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private boolean checkUserScoreExists(int loggedInUserId){
        return repository.userScoreAlreadyExist(loggedInUserId);
    }

    private void insertUserScore(Score score){
        repository.insertScore(score);
    }

    public void updateScore(int loggedInUserId){
       LiveData<Score> scoreObserver = repository.getScoreByUserId(loggedInUserId);
       scoreObserver.observe(this, score -> {
           if(score != null){
               if(currentUserScore.getUserScore() > score.getUserScore()){
                   currentUserScore.setScoreId(score.getScoreId());
                   insertUserScore(currentUserScore);
               }
           }
       });
    }

    private void checkLoggedInUser() {
        LiveData<UserInfo> userObserver;

        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(LandingActivity.SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        loggedInUser = sharedPreferences.getString(LandingActivity.SHARED_PREFERENCE_USERNAME_KEY, LOGGED_OUT_USERNAME);

        if (loggedInUserId != LOGGED_OUT) {
            return;
        }

        userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, userInfo -> {
            if (userInfo != null) {
                return;
            }
        });
    }

    public static Intent intentFactory(Context context, int userScore, int userStrikes, int totalAnsweredQuestions, double totalTime, String gameModeName){
        Intent intent = new Intent(context, PlayResultsActivity.class);
        intent.putExtra(USER_SCORE_NAME, userScore);
        intent.putExtra(USER_STRIKE_NAME, userStrikes);
        intent.putExtra(TOTAL_QUESTIONS_ANSWERED_NAME, totalAnsweredQuestions);
        intent.putExtra(USER_TIME_NAME, totalTime);
        intent.putExtra(GAME_MODE_NAME, gameModeName);
        return intent;
    }
}