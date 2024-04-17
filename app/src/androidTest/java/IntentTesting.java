import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.room.Update;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.eggert_hoppens_project2.AdminActivity;
import com.example.eggert_hoppens_project2.GameModeActivity;
import com.example.eggert_hoppens_project2.LandingActivity;
import com.example.eggert_hoppens_project2.MainActivity;
import com.example.eggert_hoppens_project2.PlayActivity;
import com.example.eggert_hoppens_project2.PlayResultsActivity;
import com.example.eggert_hoppens_project2.ProfileActivity;
import com.example.eggert_hoppens_project2.R;
import com.example.eggert_hoppens_project2.ScoreboardActivity;
import com.example.eggert_hoppens_project2.SettingsActivity;
import com.example.eggert_hoppens_project2.SignUpActivity;
import com.example.eggert_hoppens_project2.UpdateQuestionActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntentTesting {


    public ActivityScenario<SignUpActivity> signUpActivityActivityScenario;
    public ActivityScenario<LandingActivity> landingActivityActivityScenario;
    public ActivityScenario<AdminActivity> adminActivityActivityScenario;
    public ActivityScenario<ScoreboardActivity> scoreboardActivityActivityScenario;
    public ActivityScenario<SettingsActivity> settingsActivityActivityScenario;
    public ActivityScenario<ProfileActivity> profileActivityActivityScenario;
    public ActivityScenario<PlayActivity> playActivityActivityScenario;
    public ActivityScenario<PlayResultsActivity> playResultsActivityActivityScenario;
    public ActivityScenario<GameModeActivity> gameModeActivityActivityScenario;
    public ActivityScenario<UpdateQuestionActivity> updateQuestionActivityActivityScenario;
    public ActivityScenario<MainActivity> mainActivityActivityScenario;

    @Before
    public void setUp() {;
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void test_toSignUpActivity() {
        mainActivityActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.toSignUp_Button)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
        mainActivityActivityScenario.close();
    }

    @Test
    public void test_toPlayActivity() {
        landingActivityActivityScenario = ActivityScenario.launch(LandingActivity.class);
        onView(withId(R.id.play_Button)).perform(click());
        intended(hasComponent(PlayActivity.class.getName()));
        landingActivityActivityScenario.close();
    }


    @Test
    public void test_toLanding() {
        settingsActivityActivityScenario = ActivityScenario.launch(SettingsActivity.class);
        onView(withId(R.id.settingsBackButton)).perform(click());
        intended(hasComponent(LandingActivity.class.getName()));
        settingsActivityActivityScenario.close();
    }

    @Test
    public void test_toSettings() {
        landingActivityActivityScenario = ActivityScenario.launch(LandingActivity.class);
        onView(withId(R.id.settings_Button)).perform(click());
        intended(hasComponent(SettingsActivity.class.getName()));
        landingActivityActivityScenario.close();
    }
}