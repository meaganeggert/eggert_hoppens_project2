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
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityCategoryBinding;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    private AppRepository repository;

    public static final String TAG = "EGGHOP_CATEGORY";

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        checkLoggedInUser();

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        //Upon selecting the first category option, use the intent factory from the GameModeActivity
        //and pass an extra for the selected category's name.
        binding.categoryFirstOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = binding.categoryFirstOptionButton.getText().toString();
                if(checkCategoryExists(categoryName)){
                    Intent intent = GameModeActivity.intentFactory(
                            CategoryActivity.this,
                            categoryName);
                    startActivity(intent);
                }else{
                    Toast.makeText(CategoryActivity.this,
                            "No questions related to " + categoryName + " at the moment.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Upon selecting the second category option, use the intent factory from the GameModeActivity
        //and pass an extra for the selected category's name.
        binding.categorySecondOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = binding.categorySecondOptionButton.getText().toString();
                if(checkCategoryExists(categoryName)){
                    Intent intent = GameModeActivity.intentFactory(
                            CategoryActivity.this,
                            categoryName);
                    startActivity(intent);
                }else{
                    Toast.makeText(CategoryActivity.this,
                            "No questions related to " + categoryName + " at the moment.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean checkCategoryExists(String category){
        boolean exists = false;
        exists = repository.doesContainCategory(category);
        return exists;
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

    /**
     * CategoryActivity intent factory intended to be used within the LoginActivity (which is
     * actually the main activity). Utilizes the context of the previous activity (LoginActivity).
     * @param context Context of the previous activity (LoginActivity)
     * @return Returns intent to be used to start CategoryActivity
     */
    public static Intent intentFactory(Context context){
         return new Intent(context, CategoryActivity.class);
    }
}