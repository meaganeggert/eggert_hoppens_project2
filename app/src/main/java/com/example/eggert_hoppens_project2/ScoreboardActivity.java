package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eggert_hoppens_project2.databinding.ActivityScoreboardBinding;

public class ScoreboardActivity extends AppCompatActivity {

    ActivityScoreboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scoreboardDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        //Can likely be done some other way, this is just to go back to the main menu
        binding.scoreboardBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.intentFactory(ScoreboardActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context){
        return new Intent(context, ScoreboardActivity.class);
    }
}