package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityUpdateQuestionBinding;
import com.example.eggert_hoppens_project2.databinding.ActivityUpdateUserAccountsBinding;

public class UpdateUserAccountsActivity extends AppCompatActivity {

    ActivityUpdateUserAccountsBinding binding;
    private AppRepository repository;

    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";

    String buttonSelected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkLoggedInUser();

        // Show persistent UserName
        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);

        // Delete User Functionality
        binding.deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelected = binding.deleteUsersButton.getText().toString();
                loadFragment(DeleteUserFragment.newInstance(loggedInUserId));
            }
        });

        // Back Button Functionality
        binding.updateUsersBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.intentFactory(UpdateUserAccountsActivity.this);
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
     * This will load the fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.updateUsers_FragmentContainer, fragment);
        fragTran.commit();
    }

    public static Intent intentFactory (Context context){
        return new Intent(context, UpdateUserAccountsActivity.class);
    }

}