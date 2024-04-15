package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityPlayResultsScreenBinding;

import java.util.Locale;

public class PlayResultsActivity extends AppCompatActivity {
    private AppRepository repository;
    ActivityPlayResultsScreenBinding binding;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayResultsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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