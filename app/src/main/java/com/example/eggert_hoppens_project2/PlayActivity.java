package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggert_hoppens_project2.databinding.ActivityPlayBinding;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity {

    private static final String GAME_MODE_NAME = "Game Mode Name";
    private static final String CATEGORY_NAME = "Category_Name_Value_String";

    private static String gameModeName = "";
    private static String categoryName = "";


    private int score = 0;
    private int currentQuestionNumber = 0;

    String userAnswer = "";


    TextView questionTextView;
    TextView questionNumberTextView;
    Button ans1, ans2, ans3, ans4;
  
    ActivityPlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questionTextView = findViewById(R.id.questionTextView);

        questionNumberTextView = findViewById(R.id.questionNumberTextView);

        ans1 = findViewById(R.id.answerFirstButton);
        ans2 = findViewById(R.id.answerSecondButton);
        ans3 = findViewById(R.id.answerThirdButton);
        ans4 = findViewById(R.id.answerFourthButton);


        //If the previous activity passed an extra for the selected game mode, show the selected
        //game mode on screen using the playPassedGameModeTextView text view.
        if(getIntent().hasExtra(GAME_MODE_NAME)){
            gameModeName = getIntent().getStringExtra(GAME_MODE_NAME);
            binding.playPassedGameModeTextView.setText(String.format(Locale.US, "%s", gameModeName));
            //TODO: use game mode rules based on string name.
        }
        //If the previous activity passed an extra for the selected category, show the selected
        //game mode on screen using the playPassedCategoryTextView text view.
        if(getIntent().hasExtra(CATEGORY_NAME)){
            categoryName = getIntent().getStringExtra(CATEGORY_NAME);
            binding.playPassedCategoryTextView.setText(String.format(Locale.US, "Category: %s", categoryName));
        }

        //-- BEGIN Quit Button Functionality --
        binding.quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.intentFactory(getApplicationContext(), 99);
                startActivity(intent);
            }
        });
        //-- END Quit Button Functionality --
    }



    /**
     * PlayActivity intent factory intended to be used within GameModeActivity. Utilizes the context
     * of the previous activity (GameModeActivity), and passes the extras for the names of the
     * selected game mode and category.
     * @param context Context of the previous activity (GameModeActivity)
     * @param gameMode Name of the selected game mode
     * @param category Name of the selected category of questions
     * @return Returns intent to be used to start the PlayActivity
     */
    public static Intent intentFactory(Context context, String gameMode, String category){
        //TODO: also pass extra containing genre.
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(GAME_MODE_NAME, gameMode);
        intent.putExtra(CATEGORY_NAME, category);
        return intent;
    }
}