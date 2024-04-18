package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityGameModeBinding;

import java.util.Locale;

public class GameModeActivity extends AppCompatActivity {
    private AppRepository repository;

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    private static final String CATEGORY_NAME = "Category_Name_Value_String";
    private static String categoryName = "";
    ActivityGameModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkLoggedInUser();

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        //If the previous activity passed an extra for the selected game mode, show the selected
        //game mode on screen using the playPassedGameModeTextView text view.
        if(getIntent().hasExtra(CATEGORY_NAME)){
            categoryName = getIntent().getStringExtra(CATEGORY_NAME);
            binding.gameModePassedCategoryTextView.setText(String.format(Locale.US, "Category: %s", categoryName));
        }

        //Upon selecting the first game mode option, use the intent factory from the PlayActivity
        //and pass an extra for the selected game mode's name and pass the extra for the
        //selected category.
        binding.gameModeFirstOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeFirstOptionButton.getText().toString(),
                        categoryName
                );
                startActivity(intent);
            }
        });

        //Upon selecting the second game mode option, use the intent factory from the PlayActivity
        //and pass an extra for the selected game mode's name and pass the extra for the
        //selected category.
        binding.gameModeSecondOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeSecondOptionButton.getText().toString(),
                        categoryName
                );
                startActivity(intent);
            }
        });

        //Upon selecting the third game mode option, use the intent factory from the PlayActivity
        //and pass an extra for the selected game mode's name and pass the extra for the
        //selected category.
        binding.gameModeThirdOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeThirdOptionButton.getText().toString(),
                        categoryName
                );
                startActivity(intent);
            }
        });

    }

    /**
     * This will pull information from our Shared Preferences in order to
     * display the current username in the toolbar
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

    /**
     * GameModeActivity intent factory intended to be used within the CategoryActivity, utilizing the
     * context of the Category Activity and passing the name of the selected category as an extra.
     * @param context Context of previous activity (CategoryActivity)
     * @param category Name of the selected category of questions
     * @return Returns intent to be used for starting GameModeActivity
     */
    public static Intent intentFactory(Context context, String category){
        Intent intent = new Intent(context, GameModeActivity.class);
        intent.putExtra(CATEGORY_NAME, category);
        return intent;
    }
}