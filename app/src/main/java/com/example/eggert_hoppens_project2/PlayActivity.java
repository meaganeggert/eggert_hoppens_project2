package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eggert_hoppens_project2.databinding.ActivityPlayBinding;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity {

    private static final String GAMEMODE_NAME = "Game Mode Name";
    ActivityPlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra(GAMEMODE_NAME)){
            String gameModeName = getIntent().getStringExtra(GAMEMODE_NAME);
            binding.playPassedGameModeTextView.setText(String.format(Locale.US, "%s", gameModeName));
            //TODO: use game mode rules based on string name.
        }
    }

    public static Intent intentFactory(Context context, String gameMode){
        //TODO: also pass extra containing genre.
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(GAMEMODE_NAME, gameMode);
        return intent;
    }
}