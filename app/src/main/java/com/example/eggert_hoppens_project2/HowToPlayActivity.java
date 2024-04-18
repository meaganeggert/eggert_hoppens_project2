package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityHowToPlayBinding;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HowToPlayActivity extends AppCompatActivity {
    ActivityHowToPlayBinding binding;

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";
    private AppRepository repository;
    List<String> listOfSteps = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHowToPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkLoggedInUser();



        listOfSteps.add("1.\tIn the main menu, select play.\n\n");
        listOfSteps.add("2.\tSelect the categegory of questions you want to answer.\n\n");
        listOfSteps.add("3.\tSelect the game mode you want to play.\n\n");
        listOfSteps.add("4.\tYou will now be able to play, read the question carefully\n");
        listOfSteps.add("5.\tSelect an answer, then submit.\n\n");
        listOfSteps.add("6.\tRepeat until there are no more questions, you quit, or get three strikes if you selected the compete game mode.\n\n");
        listOfSteps.add("7.\tOnce you are done, you will see your results, and can go back to the main menu.\n\n");



        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        binding.howToPlayInfoTextView.setMovementMethod(new ScrollingMovementMethod());

        printInstructions(listOfSteps);

        binding.howToPlayBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SettingsActivity.intentFactory(HowToPlayActivity.this);
                startActivity(intent);
            }
        });

    }

    public void printInstructions(List<String> list){
        StringBuilder sb = new StringBuilder();

        for(String str: list){
            sb.append(str);
        }

        binding.howToPlayInfoTextView.setText(sb);
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

        try {
            userObserver = repository.getUserByUserId(loggedInUserId);
            userObserver.observe(this, userInfo -> {
                if (userInfo != null) {
                    return;
                }
            });
        } catch (NullPointerException e) {
            Log.d("CHECKFOREXCEPTION", e.toString());
            return;
        }
    }

    public static Intent intentFactory(Context context){
        return new Intent(context, HowToPlayActivity.class);
    }
}