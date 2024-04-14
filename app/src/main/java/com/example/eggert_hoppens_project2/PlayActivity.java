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
import com.example.eggert_hoppens_project2.gamemodes.GameModeSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity {
    private AppRepository repository;
    private static final String GAME_MODE_NAME = "Game Mode Name";  //extra game mode name for intent factory
    private static final String CATEGORY_NAME = "Category_Name_Value_String";   //extra category name for intent factory
    private static String gameModeName = "";    //extra game mode value
    private static String categoryName = "";    //extra category value

    private String dbQuestion = "";
    private String dbCorrectAnswer = "Correct";
    private ArrayList<String> dbAnswerChoices = new ArrayList<>();
    private int dbTotalQuestions = 0;
    private int score = 0;
    private int currentQuestionIndex = 0;
    private String userAnswer = "";

    GameModeSettings gameModeSettings;
  
    ActivityPlayBinding binding;
    TextView questionTextView;
    TextView questionNumberTextView;
    Button ans1, ans2, ans3, ans4;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        //Set fields for text and buttons to edit
        setViewBindings();

        //If the previous activity passed an extra for the selected game mode, show the selected
        //game mode on screen using the playPassedGameModeTextView text view.
        if(getIntent().hasExtra(GAME_MODE_NAME)){
            gameModeName = getIntent().getStringExtra(GAME_MODE_NAME);
            binding.playPassedGameModeTextView.setText(String.format(Locale.US, "%s", gameModeName));
            gameModeSettings = new GameModeSettings(gameModeName);
        }
        //If the previous activity passed an extra for the selected category, show the selected
        //game mode on screen using the playPassedCategoryTextView text view.
        if(getIntent().hasExtra(CATEGORY_NAME)){
            categoryName = getIntent().getStringExtra(CATEGORY_NAME);
            binding.playPassedCategoryTextView.setText(String.format(Locale.US, "Category: %s", categoryName));
        }


        //TODO: Fix so
        setTotalQuestions();

        loadNewQuestion();

        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userAnswer.isEmpty()){
                    userAnswer = null;
                }
                userAnswer = ans1.getText().toString();
            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userAnswer.isEmpty()){
                    userAnswer = null;
                }
                userAnswer = ans2.getText().toString();
            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userAnswer.isEmpty()){
                    userAnswer = null;
                }
                userAnswer = ans3.getText().toString();
            }
        });
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userAnswer.isEmpty()){
                    userAnswer = null;
                }
                userAnswer = ans4.getText().toString();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAnswer.isEmpty()){
                    Toast.makeText(PlayActivity.this, "Must select an answer.", Toast.LENGTH_SHORT).show();
                } else{
                    if(userAnswer.equals(dbCorrectAnswer)){
                        score++;
                    }
                    if(currentQuestionIndex >= dbTotalQuestions - 1){
                        Intent intent = CategoryActivity.intentFactory(PlayActivity.this);
                        startActivity(intent);
                    } else{
                        currentQuestionIndex++;
                        loadNewQuestion();
                    }
                }
            }
        });

    }

    /**
     * loadNewQuestion will read from the database's question table by category, then assign the
     * temporary fields of question info by the current question number. Once the temporary fields
     * are established, call the assignQuestionToView() method to display the info on screen.
     */
    public void loadNewQuestion(){
        LiveData<List<Question>> categoryObserver = repository.getAllQuestionsByCategory(categoryName);
        categoryObserver.observe(this, questions -> {
            if(!questions.isEmpty()){
                if(currentQuestionIndex < questions.size()){
                    dbQuestion = questions.get(currentQuestionIndex).getQuestion();
                    dbCorrectAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();

                    //Correct answer is included so we can randomize its placement for the quiz later.
                    //Can likely be done another way, but I struggled to get this to work with a normal
                    //string array. Maybe I messed up, but for now this will do.
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getCorrectAnswer());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer1());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer2());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer3());
                }
            }
            assignQuestionToView();
            dbAnswerChoices.clear();
        });

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
     * setTotalQuestions() method reads from the database's question table and assigns the
     * dbTotalQuestions field to the tables size;
     */
    public void setTotalQuestions(){
        LiveData<List<Question>> categoryObserver = repository.getAllQuestionsByCategory(categoryName);
        categoryObserver.observe(this, questionList -> {
            if(!questionList.isEmpty()){
                dbTotalQuestions = questionList.size();
            }
        });
    }


    public void setViewBindings(){
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        ans1 = findViewById(R.id.answerFirstButton);
        ans2 = findViewById(R.id.answerSecondButton);
        ans3 = findViewById(R.id.answerThirdButton);
        ans4 = findViewById(R.id.answerFourthButton);
        submitButton = findViewById(R.id.submitButton);
    }

    //TODO: assign the choices randomly
    public void assignQuestionToView(){
        questionNumberTextView.setText(String.format(Locale.US, "Question %d", currentQuestionIndex+1));
        questionTextView.setText(String.format(Locale.US, "%s", dbQuestion));

        ans1.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(0)));
        ans2.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(1)));
        ans3.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(2)));
        ans4.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(3)));
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