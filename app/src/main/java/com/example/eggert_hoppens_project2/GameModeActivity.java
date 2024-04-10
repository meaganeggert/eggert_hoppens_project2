package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggert_hoppens_project2.databinding.ActivityGameModeBinding;

import java.util.Locale;

public class GameModeActivity extends AppCompatActivity {
    private static final String CATEGORY_NAME = "Category_Name_Value_String";
    ActivityGameModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra(CATEGORY_NAME)){
            String category = getIntent().getStringExtra(CATEGORY_NAME);
            binding.gameModePassedCategoryTextView.setText(String.format(Locale.US, "%s", category));
        }

        binding.gameModeFirstOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeFirstOptionButton.getText().toString());
                startActivity(intent);
            }
        });

        binding.gameModeSecondOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeSecondOptionButton.getText().toString());
                startActivity(intent);
            }
        });

        binding.gameModeThirdOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayActivity.intentFactory(
                        GameModeActivity.this,
                        binding.gameModeThirdOptionButton.getText().toString());
                startActivity(intent);
            }
        });

    }

    public static Intent intentFactory(Context context, String category){
        Intent intent = new Intent(context, GameModeActivity.class);
        intent.putExtra(CATEGORY_NAME, category);
        return intent;
    }
}