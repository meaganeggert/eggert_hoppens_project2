package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.databinding.ActivityPlayBinding;

import java.util.List;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity {
    private AppRepository repository;
    private static final String GAME_MODE_NAME = "Game Mode Name";
    private static final String CATEGORY_NAME = "Category_Name_Value_String";
    private static String gameModeName = "";
    private static String categoryName = "";

    private int score = 0;
    private int currentQuestionIndex = 0;

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
        repository = AppRepository.getRepository(getApplication());

        //Identifiers for text and buttons for editing
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

        //Set questions to textview and buttons. Needs to come after reading the extra as we use it
        //to select questions based on category.
        loadNewQuestion();

    }

    //Using live data from question table in the database, set the textview and buttons
    //to the corresponding data. This is very basic as it always puts the correct question
    //in the same spot. TODO: make the question placement random.
    public void loadNewQuestion(){
        LiveData<List<Question>> categoryObserver = repository.getAllQuestionsByCategory(categoryName);
        categoryObserver.observe(this, questionList -> {
            if(!questionList.isEmpty()){
                questionTextView.setText(questionList.get(currentQuestionIndex).getQuestion());
                ans1.setText(questionList.get(currentQuestionIndex).getCorrectAnswer());
                ans2.setText(questionList.get(currentQuestionIndex).getIncorrectAnswer1());
                ans3.setText(questionList.get(currentQuestionIndex).getIncorrectAnswer2());
                ans4.setText(questionList.get(currentQuestionIndex).getIncorrectAnswer3());
            }
        });
    }

    /**
     * PlayActivity intent factory inteded to be used within GameModeActivity. Utilizes the context
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