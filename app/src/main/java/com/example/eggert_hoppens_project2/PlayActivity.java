package com.example.eggert_hoppens_project2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivityPlayBinding;
import com.example.eggert_hoppens_project2.gamemodes.GameModeSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private String loggedInUser = "bob";
    private int loggedInUserId = -1;
    private static final int LOGGED_OUT = -1;
    private static final String LOGGED_OUT_USERNAME = "EGGHOP";
    private AppRepository repository;

    private static final String CHANNEL_ID = "REPORT_NOTIFY";

    private static final String GAME_MODE_NAME = "Game Mode Name";  //extra game mode name for intent factory
    private static final String CATEGORY_NAME = "Category_Name_Value_String";   //extra category name for intent factory
    private static String gameModeName = "";    //extra game mode value
    private static String categoryName = "";    //extra category value

    private String dbQuestion = "";
    private int dbQuestionId = -1;
    private String dbCorrectAnswer = "Correct";
    private final ArrayList<String> dbAnswerChoices = new ArrayList<>();
    private int dbTotalQuestions = 0;
    private int currentQuestionIndex = 0;
    private String userAnswer = "";

    GameModeSettings gameModeSettings;

    ActivityPlayBinding binding;
    TextView questionTextView;
    TextView questionNumberTextView;
    Button ans1, ans2, ans3, ans4;
    Button submitButton, quitButton;
    Button reportButton;

    private boolean check1 = false;
    private boolean check2 = false;
    private boolean check3 = false;
    private boolean check4 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        checkLoggedInUser();

        //Set fields for text and buttons to edit
        setViewBindings();

        //If the previous activity passed an extra for the selected game mode, show the selected
        //game mode on screen using the playPassedGameModeTextView text view.
        if (getIntent().hasExtra(GAME_MODE_NAME)) {
            gameModeName = getIntent().getStringExtra(GAME_MODE_NAME);
            binding.playPassedGameModeTextView.setText(String.format(Locale.US, "%s", gameModeName));
            gameModeSettings = new GameModeSettings(gameModeName);
        }
        //If the previous activity passed an extra for the selected category, show the selected
        //game mode on screen using the playPassedCategoryTextView text view.
        if (getIntent().hasExtra(CATEGORY_NAME)) {
            categoryName = getIntent().getStringExtra(CATEGORY_NAME);
            binding.playPassedCategoryTextView.setText(String.format(Locale.US, "Category: %s", categoryName));
        }

        //_______________________________________________________________________________________________________________
        //Most of the play functionality will take place from here on out.

        setTotalQuestions();    //Set question list size so we know when to stop getting questions.
        loadNewQuestion();      //Load the question to be used for the activity.

        //For each answerButton, set the user's answer to the button they selected.
        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userAnswer.isEmpty()) { //This shouldn't happen, but just to be safe.
                    userAnswer = "";
                }
                userAnswer = ans1.getText().toString();
                if (!check1) {
                    ans1.setBackgroundColor(Color.parseColor("TEAL")); // PRESSED BUTTON COLOR
                    ans4.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans2.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans3.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check1 = !check1;
                    check4 = false;
                    check2 = false;
                    check3 = false;
                } else {
                    ans1.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check1 = !check1;
                }
            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userAnswer.isEmpty()) { //This shouldn't happen, but just to be safe.
                    userAnswer = "";
                }
                userAnswer = ans2.getText().toString();
                if (!check2) {
                    ans2.setBackgroundColor(Color.parseColor("TEAL")); // PRESSED BUTTON COLOR
                    ans1.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans4.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans3.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check2 = !check2;
                    check1 = false;
                    check4 = false;
                    check3 = false;
                } else {
                    ans2.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check2 = !check2;
                }
            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userAnswer.isEmpty()) { //This shouldn't happen, but just to be safe.
                    userAnswer = "";
                }
                userAnswer = ans3.getText().toString();
                if (!check3) {
                    ans3.setBackgroundColor(Color.parseColor("TEAL")); // PRESSED BUTTON COLOR
                    ans1.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans2.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans4.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check3 = !check3;
                    check1 = false;
                    check2 = false;
                    check4 = false;
                } else {
                    ans3.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check3 = !check3;
                }
            }
        });


        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userAnswer.isEmpty()) {  //This shouldn't happen, but just to be safe.
                    userAnswer = "";
                }
                userAnswer = ans4.getText().toString();
                if (!check4) {
                    ans4.setBackgroundColor(Color.parseColor("TEAL")); // PRESSED BUTTON COLOR
                    ans1.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans2.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans3.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check4 = !check4;
                    check1 = false;
                    check2 = false;
                    check3 = false;
                } else {
                    ans4.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    check4 = !check4;
                }
            }
        });

        //Clicking the submit button will check if an answer has been selected and will check if it is
        //correct. It will also check if we have the user has made it to the end of the list to
        //to prevent a crash. Doing so will send the user somewhere.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAnswer.isEmpty()) {
                    Toast.makeText(PlayActivity.this, "Must select an answer.", Toast.LENGTH_SHORT).show();
                } else {
                    ans1.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans2.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans3.setBackgroundColor(Color.parseColor("#6750A4")); // DEFAULT BUTTON COLOR
                    ans4.setBackgroundColor(Color.parseColor("#6750A4")); // PRESSED BUTTON COLOR
                    check1 = false;
                    check2 = false;
                    check3 = false;
                    check4 = false;
                    if (userAnswer.equals(dbCorrectAnswer)) {
                        gameModeSettings.incrementScore();
                    } else {
                        gameModeSettings.incrementStrikes();
                    }
                    if (gameModeSettings.checkGameEnds()) {
                        gameModeSettings.endGame();
                        Intent intent = PlayResultsActivity.intentFactory(
                                PlayActivity.this,
                                gameModeSettings.getUserScore(),
                                gameModeSettings.getUserStrikes(),
                                gameModeSettings.getCurrentQuestionIndex() + 1,
                                gameModeSettings.getTotalTime(),
                                gameModeSettings.getGameModeName()
                        );
                        startActivity(intent);
                    } else {
                        userAnswer = "";
                        currentQuestionIndex++;
                        gameModeSettings.setCurrentQuestionIndex(currentQuestionIndex);
                        loadNewQuestion();
                    }
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameModeSettings.endGame();
                Intent intent = PlayResultsActivity.intentFactory(
                        PlayActivity.this,
                        gameModeSettings.getUserScore(),
                        gameModeSettings.getUserStrikes(),
                        gameModeSettings.getCurrentQuestionIndex(),
                        gameModeSettings.getTotalTime(),
                        gameModeSettings.getGameModeName()
                );
                startActivity(intent);
            }
        });

        // Saves the question number to SharedPreferences and sends up a notification that the question has been reported
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportQuestion();
                notifyMe(getApplicationContext());
            }
        });

    }

    /**
     * loadNewQuestion will read from the database's question table by category, then assign the
     * question related field values by the current question number. Once the temporary fields
     * are established, call the assignQuestionToView() method to display the info on screen.
     */
    public void loadNewQuestion() {
        LiveData<List<Question>> categoryObserver = repository.getAllQuestionsByCategory(categoryName);
        categoryObserver.observe(this, questions -> {
            if (!questions.isEmpty()) {
                if (currentQuestionIndex < questions.size()) {
                    dbQuestion = questions.get(currentQuestionIndex).getQuestion();
                    dbQuestionId = questions.get(currentQuestionIndex).getQuestionId();
                    dbCorrectAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();

                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getCorrectAnswer());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer1());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer2());
                    dbAnswerChoices.add(questions.get(currentQuestionIndex).getIncorrectAnswer3());
                }
            }
            assignQuestionToView();
            //do not clear dbAnswerChoices here because we still need it to randomize its associated button in assignQuestionToView();
        });
    }

    /**
     * setTotalQuestions() method reads from the database's question table and assigns the
     * dbTotalQuestions field to the tables size. Mostly done separately so we don't keep
     * reassigning the dbTotalQuestions to the same thing over and over again.
     */
    public void setTotalQuestions() {
        LiveData<List<Question>> categoryObserver = repository.getAllQuestionsByCategory(categoryName);
        categoryObserver.observe(this, questionList -> {
            if (!questionList.isEmpty()) {
                dbTotalQuestions = questionList.size();
                gameModeSettings.setDbTotalQuestions(dbTotalQuestions);
            }
        });
    }

    /**
     * assignQuestionToView() method allows the answer choices for the current question to be
     * displayed in a random order of buttons, displays the question itself, and the question
     * number.
     */
    public void assignQuestionToView() {
        Random rand = new Random();
        int tempIndex;

        //This portion can likely be done in a while loop or something
        //TODO: use a loop here if we have extra time.
        tempIndex = rand.nextInt(dbAnswerChoices.size());
        ans1.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(tempIndex)));
        dbAnswerChoices.remove(tempIndex);

        tempIndex = rand.nextInt(dbAnswerChoices.size());
        ans2.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(tempIndex)));
        dbAnswerChoices.remove(tempIndex);

        tempIndex = rand.nextInt(dbAnswerChoices.size());
        ans3.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(tempIndex)));
        dbAnswerChoices.remove(tempIndex);

        tempIndex = rand.nextInt(dbAnswerChoices.size());
        ans4.setText(String.format(Locale.US, "%s", dbAnswerChoices.get(tempIndex)));
        dbAnswerChoices.remove(tempIndex);

        //make sure arrayList is clear to use for next question.
        dbAnswerChoices.clear();

        questionNumberTextView.setText(String.format(Locale.US, "Question %d", currentQuestionIndex + 1));
        questionTextView.setText(String.format(Locale.US, "%s", dbQuestion));
    }

    /**
     * setViewBindings() sets the buttons and text views that will be edited... which is probably
     * all of them. TODO: continue adding on to this, then change the description of this.
     */
    public void setViewBindings() {
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        ans1 = findViewById(R.id.answerFirstButton);
        ans2 = findViewById(R.id.answerSecondButton);
        ans3 = findViewById(R.id.answerThirdButton);
        ans4 = findViewById(R.id.answerFourthButton);
        submitButton = findViewById(R.id.submitButton);
        quitButton = findViewById(R.id.quitButton);
        reportButton = findViewById(R.id.report_Button);

        TextView toolbar_UserName = (TextView) findViewById(R.id.toolbarUsername);
        toolbar_UserName.setText(loggedInUser);
    }

    /**
     * Sends the question ID to properly address the reported question
     */
    private void reportQuestion() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_REPORTED_QUEST_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(LandingActivity.SHARED_PREFERENCE_REPORTED_QUEST_ID, dbQuestionId);
        sharedPrefEditor.apply();
        Toast.makeText(this, "Reported Question " + dbQuestionId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates a reported question notification that pops up with a banner
     */
    private void notifyMe(Context context) {
        NotificationManager notifyManager = createNotificationChannel(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.bubble_icon)
                .setContentTitle("Question #" + dbQuestionId + " Reported")
                .setContentText("You should consider deleting this question.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
        //notifyManager.notify(1, builder.build());

    }

    /**
     * This method creates the channel for implementing push notifications
     */
    private NotificationManager createNotificationChannel(Context context) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            return notificationManager;
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
     * PlayActivity intent factory intended to be used within GameModeActivity. Utilizes the context
     * of the previous activity (GameModeActivity), and passes the extras for the names of the
     * selected game mode and category.
     *
     * @param context  Context of the previous activity (GameModeActivity)
     * @param gameMode Name of the selected game mode
     * @param category Name of the selected category of questions
     * @return Returns intent to be used to start the PlayActivity
     */
    public static Intent intentFactory(Context context, String gameMode, String category) {
        //TODO: also pass extra containing genre.
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(GAME_MODE_NAME, gameMode);
        intent.putExtra(CATEGORY_NAME, category);
        return intent;
    }
}