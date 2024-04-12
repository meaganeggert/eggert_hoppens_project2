package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    static final String SHARED_PREFERENCE_USERID_KEY = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "eggert_hoppens_project2.LOGIN_ACTIVITY_SHARED_PREFERENCE_USERID_VALUE";
    private static final String CATEGORY_NAME = "Category_Name_Value_String";
    private static final String USER_NAME = "logged_In_User";
    private static final String LOGIN_ACT_USER_ID = "login_activity_user_id";

    private AppRepository repository;

    private String loggedInUser = "testUser";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        loginUser();
        loggedInUserId = getIntent().getIntExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);
        Toast.makeText(this, String.valueOf(loggedInUserId), Toast.LENGTH_SHORT).show();
        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = MainActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Show persistent username
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            loggedInUser = b.getString(USER_NAME);
        }
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        // Set up for Header Toolbar
        Toolbar thisToolbar = (Toolbar) findViewById(R.id.headerToolbar);
        setSupportActionBar(thisToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CategoryActivity.intentFactory(LoginActivity.this);
                startActivity(intent);
            }
        });

    }

//    private void loginUser() {
//        // check shared preference for logged in user
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
//        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
//        if (loggedInUserId != LOGGED_OUT) {
//            return;
//        }
//
//        // check intent for logged in user
//        loggedInUserId = getIntent().getIntExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);
//        if (loggedInUserId == LOGGED_OUT) {
//            return;
//        }
//        LiveData<UserInfo> userObserver = repository.getUserByUserId(loggedInUserId);
//        userObserver.observe(this, userInfo -> {
//            if (userInfo != null) {
//                return;
//            }
//        });
//    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor= sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.apply();
        getIntent().putExtra(LOGIN_ACT_USER_ID, LOGGED_OUT);

        startActivity(MainActivity.intentFactory(getApplicationContext()));
    }

    /**
     * Intent Factory for the LoginActivity
     * @param context The context that the intent factory was called from
     * @return The intent involving this class
     */
    static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LOGIN_ACT_USER_ID, userId);
        return intent;
    }
}