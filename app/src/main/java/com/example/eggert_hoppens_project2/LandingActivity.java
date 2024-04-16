package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LandingActivity extends AppCompatActivity {

    static final String SHARED_PREFERENCE_USERID_KEY = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERNAME_KEY = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_USERNAME_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_USERID_VALUE";
    static final String SHARED_PREFERENCE_ISADMIN_KEY = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_ISADMIN_KEY";
    static final String SHARED_PREFERENCE_REPORTED_QUEST_ID = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_REPORTED_QUESTION_ID";

    private static final String CATEGORY_NAME = "Category_Name_Value_String";
    private static final String USER_NAME = "logged_In_User";
    private static final String LOGIN_ACT_USER_ID = "login_activity_user_id";


    private AppRepository repository;

    private static final String LOGGED_OUT_USERNAME = "EGGHOP";
    private String loggedInUser = "egghop";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final int NO_REPORTS = -1;

    private boolean loggedIn_isAdmin = false;

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserId = getIntent().getIntExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);
        loginUser();
//        Toast.makeText(this, String.valueOf(loggedInUserId), Toast.LENGTH_SHORT).show();


        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = MainActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Only show "Test" button if needed for debugging
        if (MainActivity.DEBUG) {
            binding.testButton.setVisibility(View.VISIBLE);
        }

        //Only show "Admin" options if the logged in user is an administrator
        if (loggedIn_isAdmin) {
            binding.adminSettingsButton.setVisibility(View.VISIBLE);
        }

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        // Set up for Header Toolbar
        Toolbar thisToolbar = (Toolbar) findViewById(R.id.headerToolbar);
        setSupportActionBar(thisToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //Pressing the play button takes you to the category selection activity
        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CategoryActivity.intentFactory(LandingActivity.this);
                startActivity(intent);
            }
        });

        //Pressing the scoreboard button takes you to the scoreboard activity
        binding.scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ScoreboardActivity.intentFactory(LandingActivity.this);
                startActivity(intent);
            }
        });

        //Pressing the settings button takes you to the settings activity
        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SettingsActivity.intentFactory(LandingActivity.this);
                startActivity(intent);
            }
        });

        //-- BEGIN Log Out Button Functionality --
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        //-- END Log Out Button Functionality --

        //-- BEGIN Admin Button Functionality --
        binding.adminSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.intentFactory(LandingActivity.this);
                startActivity(intent);
            }
        });
        //-- END Admin Button Functionality --

    }

    /**
     * This method will effectively get all of the necessary information for being 'logged in'
     * It will store the current user's ID, username, and whether or not they are an admin
     */
    private void loginUser() {

        LiveData<UserInfo> userObserver;

        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        loggedInUser = sharedPreferences.getString(SHARED_PREFERENCE_USERNAME_KEY, LOGGED_OUT_USERNAME);
        loggedIn_isAdmin = sharedPreferences.getBoolean(SHARED_PREFERENCE_ISADMIN_KEY, false);
        if (loggedInUserId != LOGGED_OUT) {
            return;
        }

        // check intent for logged in user
        loggedInUserId = getIntent().getIntExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);
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

    /**
     * This method will logout the user and reset all shared preferences
     * It will also send the user back to the main activity so they can log in again, if desired
     */
    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.putString(SHARED_PREFERENCE_USERNAME_KEY, LOGGED_OUT_USERNAME);
        sharedPrefEditor.putBoolean(SHARED_PREFERENCE_ISADMIN_KEY, false);
        sharedPrefEditor.apply();
        getIntent().putExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);

        startActivity(MainActivity.intentFactory(getApplicationContext()));
    }



    /**
     * Intent Factory for the LoginActivity
     *
     * @param context The context that the intent factory was called from
     * @return The intent involving this class
     */
    static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(LOGIN_ACT_USER_ID, userId);
        return intent;
    }
}