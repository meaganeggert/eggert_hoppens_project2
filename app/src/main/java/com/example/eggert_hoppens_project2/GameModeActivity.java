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
    private static String categoryName = "";
    ActivityGameModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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