package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggert_hoppens_project2.databinding.ActivityPlayResultsScreenBinding;

import java.util.Locale;

public class PlayResultsActivity extends AppCompatActivity {
    private static final String USER_SCORE_NAME = "User Score";
    private static final String USER_STRIKE_NAME = "User Strikes";
    private static final String USER_TIME_NAME = "User Time";

    private static int userScore = 0;
    private static int userStrikes = 0;
    private static double userTime = 0.0;

    ActivityPlayResultsScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayResultsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra(USER_SCORE_NAME)){
            userScore = getIntent().getIntExtra(USER_SCORE_NAME, 0);
            binding.resultScoreTextView.setText(String.format(Locale.US, "Score: %d", userScore));
        }
        if(getIntent().hasExtra(USER_STRIKE_NAME)){
            userStrikes = getIntent().getIntExtra(USER_STRIKE_NAME, 0);
            binding.resultStrikesTextView.setText(String.format(Locale.US, "Strikes: %d", userStrikes));
        }
        if(getIntent().hasExtra(USER_TIME_NAME)){
            userTime = getIntent().getDoubleExtra(USER_TIME_NAME, 0);
            binding.resultTimeTextView.setText(String.format(Locale.US, "Time: %.2f", userTime));
        }
    }

    public static Intent intentFactory(Context context, int userScore, int userStrikes, double totalTime){
        Intent intent = new Intent(context, PlayResultsActivity.class);
        intent.putExtra(USER_SCORE_NAME, userScore);
        intent.putExtra(USER_STRIKE_NAME, userStrikes);
        intent.putExtra(USER_TIME_NAME, totalTime);
        return intent;
    }
}