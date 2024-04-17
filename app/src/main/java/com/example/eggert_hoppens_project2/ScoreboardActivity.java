package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityScoreboardBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ScoreboardActivity extends AppCompatActivity {

    ActivityScoreboardBinding binding;
    private AppRepository repository;

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    public static ArrayList<String> scoreboard_userNames = new ArrayList<>();
    public static ArrayList<String> scoreboard_Scores = new ArrayList<>();
    private static int SCORE_SIZE;

    StringBuilder scoreInfo = new StringBuilder();


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

        // Recycler View Functionality
//        ArrayList<String> test = new ArrayList<>(Arrays.asList("hello", "goodbye"));
//        test.add("ciao");
        getAllScores();
        Adapter customAdapter = new Adapter(scoreboard_userNames, scoreboard_Scores);//_____________________________________________________________________________
        //Toast.makeText(this, String.valueOf(customAdapter.getItemCount()), Toast.LENGTH_LONG).show();
        RecyclerView recyclerView = findViewById(R.id.scoreboardList_Recycler);//____________________________________________________________________________________
        recyclerView = findViewById((R.id.scoreboardList_Recycler));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(customAdapter);



        // Return to Landing Activity
        binding.scoreboardBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreboard_userNames.clear();
                scoreboard_Scores.clear();
                Intent intent = LandingActivity.intentFactory(ScoreboardActivity.this, loggedInUserId);
                startActivity(intent);
            }
        });

    }

    /**
     * This method will populate a local ArrayList from the database to be sent to the RecyclerView for display
     */
    public void getAllScores() {
        LiveData<List<Score>> categoryObserver = repository.getAllScores();
        categoryObserver.observe(this, scoresList -> {
            if (!scoresList.isEmpty()) {
                for (Score score : scoresList) {
                    scoreboard_userNames.add(score.getUserName());
                    scoreboard_Scores.add(String.valueOf(score.getUserScore()));
                }
                Adapter customAdapter = new Adapter(scoreboard_userNames, scoreboard_Scores);
                //Toast.makeText(this, String.valueOf(customAdapter.getItemCount()), Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.scoreboardList_Recycler);
                recyclerView = findViewById((R.id.scoreboardList_Recycler));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(customAdapter);
            }
        });
    }

    /**
     * Checks to see which user is logged in for persistence use
     */
    private void checkLoggedInUser() {
        LiveData<UserInfo> userObserver;

        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(LandingActivity.SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        loggedInUser = sharedPreferences.getString(LandingActivity.SHARED_PREFERENCE_USERNAME_KEY, LOGGED_OUT_USERNAME);

        if (loggedInUserId != LOGGED_OUT) {
            return;
        }

        try {
            userObserver = repository.getUserByUserId(loggedInUserId);
            userObserver.observe(this, userInfo -> {
                if (userInfo != null) {
                    return;
                }
            });
        }
        catch (NullPointerException e) {
            Log.d("CHECKFOREXCEPTION", e.toString());
            return;
        }
    }

    public static Intent intentFactory(Context context) {

        return new Intent(context, ScoreboardActivity.class);
    }
}