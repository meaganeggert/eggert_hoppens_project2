/**
 * Names: Meagan Eggert & Brandon Hoppens
 * Detail: Main activity for the trivia game. Functions as a pre-main menu, where the user has the
 * option to log in or sign up for an account.
 */

package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppDataBase;
import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Score;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    public static final String TAG = "EGGHOP_MAIN";
    private static final String USER_NAME = "logged_In_User";
    CheckBox showPass_checkBox;
    EditText pass_editText;

    static final boolean DEBUG = false;

    private AppRepository repository;

    private AppDataBase instance;

    private String mUsername = "testUser";
    private String mPassword = "testPass";

    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;

    public static ArrayList<String> scoreboard_userNames = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());


        // Only show "Test" button if needed for debugging
        if (DEBUG) {
            binding.testButton.setVisibility(View.VISIBLE);
        }

        if (userLoggedIn()) {
            getAllScores();
            Intent intent = LandingActivity.intentFactory(this, loggedInUserId);
            intent.putStringArrayListExtra("SCOREBOARDUSERNAMES", scoreboard_userNames);
            Log.d("WTFMAIN", String.valueOf(scoreboard_userNames.size()));
            startActivity(intent);
        }

        // Set up for Header Toolbar
        Toolbar thisToolbar = (Toolbar) findViewById(R.id.headerToolbar);
        setSupportActionBar(thisToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        // This will only be used while debugging
        binding.testResultTextView.setMovementMethod(new ScrollingMovementMethod());

        //-- BEGIN Login Button Functionality --
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userVerified();
            }
        });
        //-- END Login Button Functionality --

        //-- BEGIN Sign-Up Button Functionality --
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.intentFactory(MainActivity.this);
                startActivity(intent);
            }
        });
        //-- END Sign-Up Button Functionality --

        //-- BEGIN Section for Test Button Functionality --
        binding.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                updateDisplay();
            }
        });
        //-- END Section for Test Button Functionality --

        //-- BEGIN Section For ShowPassword Checkbox Functionality --
        showPass_checkBox = findViewById(R.id.showPassword_checkBox);
        pass_editText = findViewById(R.id.password_editText);

        showPass_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // To show the password
                    pass_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // To keep the password hidden
                    pass_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //-- END Section For ShowPassword Checkbox Functionality --

    }

    /**
     * Intent Factory for the MainActivity
     * @param context The context that the intent factory was called from
     * @return The intent involving this class
     */
    static Intent intentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }

    /**
     * Updates the testResultTextView with the new information that was entered.
     * This function is only used in our application for testing purposes.
     */
    private void updateDisplay() {
        String currentInfo = binding.testResultTextView.getText().toString();
        String newDisplay = String.format(Locale.ROOT, "Username: %s%nPassword: %s%n-=-=-=-=-=-=-%n%s%n", mUsername, mPassword, currentInfo);
        binding.testResultTextView.setText(newDisplay);

    }

    /**
     * Pulls the entered information from the usernameEditText and the passwordEditText
     */
    private void getInformationFromDisplay() {
        mUsername = binding.usernameEditText.getText().toString();
        mPassword = binding.passwordEditText.getText().toString();
    }

    /**
     * Checks that the user is trying to login with correct credentials
     * @return true if valid username with valid password
     * false if invalid or mismatched
     */
    private void userVerified() {
        getInformationFromDisplay();
        if (mUsername.isEmpty()) {
            toastMaker("Username should not be blank");
            return;
        }
        LiveData<UserInfo> userObserver = repository.getUserByUserName(mUsername);
        userObserver.observe(this, userInfo -> {
            if (userInfo != null) {
                if (mPassword.equals(userInfo.getUserPassword())) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor= sharedPreferences.edit();
                    sharedPrefEditor.putInt(LandingActivity.SHARED_PREFERENCE_USERID_KEY, userInfo.getUserId());
                    sharedPrefEditor.putString(LandingActivity.SHARED_PREFERENCE_USERNAME_KEY, userInfo.getUserName());
                    sharedPrefEditor.putBoolean(LandingActivity.SHARED_PREFERENCE_ISADMIN_KEY, userInfo.isAdmin());
                    sharedPrefEditor.apply();
//                    toastMaker("You made it here");
                    Intent intent = LandingActivity.intentFactory(MainActivity.this, userInfo.getUserId());
                    startActivity(intent);
                }
                else {
                    toastMaker("Invalid Password");
                    binding.passwordEditText.setSelection(0);
                }
            }
            else {
                toastMaker(String.format("%s is not a valid username.", mUsername));
                binding.usernameEditText.setSelection(0);
            }
        });
    }

    /**
     * TODO: FILL THIS IN LATER
     * @return
     */
    private boolean userLoggedIn() {

        LiveData<UserInfo> userObserver;

        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(LandingActivity.SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        if (loggedInUserId != LOGGED_OUT) {
            return true;
        }


        userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, userInfo -> {
            if (userInfo != null) {
                return;
            }
        });
        return false;
    }

    /**
     * TODO: TEST FUNCTION
     */
    public void getAllScores() {
        LiveData<List<Score>> scoreObserver = repository.getAllScores();
        scoreObserver.observe(this, scoresList -> {
            if (!scoresList.isEmpty()) {
                for (Score score : scoresList) {
                    Log.d("CRAZY", score.getUserName());
                    scoreboard_userNames.add(score.getUserName());
                    Log.d("WHAT", scoreboard_userNames.get(scoresList.indexOf(score)));
                }
            }
        });
    }

    /**
     * This method just makes it easier to type Toast messages
     * @param message The message you want to display in the toast
     */
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}