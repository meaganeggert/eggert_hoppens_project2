package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityScoreboardBinding;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    ActivityScoreboardBinding binding;
    private AppRepository repository;

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    private static final List<Score> currentScoreList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        checkLoggedInUser();

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        binding.scoreboardDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        if(repository.doesScoreExist()){
            displayScoreList();
        }

        binding.scoreboardBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.intentFactory(ScoreboardActivity.this, loggedInUserId);
                startActivity(intent);
            }
        });

    }

    public void setCurrentScoreList(){
        int index = 0;
        LiveData<List<Score>> scoreObserver = repository.getScoresByHighest();
        scoreObserver.observe(this, scores -> {
            while(index < scores.size()){
                currentScoreList.add(scores.get(index));
            }
        });
    }

    public void displayScoreList(){
        setCurrentScoreList();
        StringBuilder scoreInfo = new StringBuilder();
        for(Score score: currentScoreList){
            scoreInfo.append(score);
        }
        binding.scoreboardDisplayTextView.setText(scoreInfo.toString());
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

    public static Intent intentFactory(Context context){
        return new Intent(context, ScoreboardActivity.class);
    }
}