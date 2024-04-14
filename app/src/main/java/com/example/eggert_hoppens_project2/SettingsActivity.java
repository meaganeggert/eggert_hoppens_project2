package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.example.eggert_hoppens_project2.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Can likely be done some other way, this is just to go back to the main menu

        binding.settingsBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
                int loggedInUserId = sharedPreferences.getInt(LandingActivity.SHARED_PREFERENCE_USERID_VALUE, -1);
                Intent intent = LandingActivity.intentFactory(SettingsActivity.this, loggedInUserId);
                startActivity(intent);
            }

        });

    }

    public static Intent intentFactory(Context context){
        return new Intent(context, SettingsActivity.class);
    }
}