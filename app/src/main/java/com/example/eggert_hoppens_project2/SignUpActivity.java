/**
 * Names: Meagan Eggert & Brandon Hoppens
 * Detail: This activity will handle a user signing up for an account within the app.
 * If the user enters a valid username and password, it will enter them into the user database.
 */

package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;
import com.example.eggert_hoppens_project2.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppRepository repository;
    public static final String TAG = "EGGHOP_SIGNUP";

    String mUsername = "testName";
    String mPassword = "testPass";
    String mRepeatPassword = "testRepeatPass";

    CheckBox showPass_checkBox;
    EditText pass_editText;
    EditText repeatPass_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up for Header Toolbar
        Toolbar thisToolbar = (Toolbar) findViewById(R.id.headerToolbar);
        setSupportActionBar(thisToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        repository = AppRepository.getRepository(getApplication());

        // Only show "Test" button if needed for debugging
        if (MainActivity.DEBUG) {
            binding.testButton.setVisibility(View.VISIBLE);
        }

        //-- BEGIN Section for toolbar "home" Functionality --
        binding.headerToolbar.toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(SignUpActivity.this);
                startActivity(intent);
            }
        });
        //-- END Section for toolbar "home" Functionality --

        //-- BEGIN Section for toolbar "login" Functionality --
        binding.headerToolbar.toolbarUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(SignUpActivity.this);
                startActivity(intent);
            }
        });
        //-- END Section for toolbar "login" Functionality --

        //-- BEGIN Section for Sign Up Button Functionality --
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                if (!userIDAvailable()) {
                    Toast.makeText(SignUpActivity.this, "That username is already taken.", Toast.LENGTH_SHORT).show();
                }
                else if (password_Validate() && !mUsername.isEmpty()) {
                    insertUser();
                    Toast.makeText(SignUpActivity.this, "Success!.", Toast.LENGTH_LONG).show();
                    returnToMain();
                }
                else if (mUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Must enter a username!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords must match and not be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //-- END Section for Sign Up Button Functionality --


        //-- BEGIN Section For ShowPassword Checkbox Functionality --
        showPass_checkBox = findViewById(R.id.showPassword_checkBox);
        pass_editText = findViewById(R.id.password_editText);
        repeatPass_editText = findViewById(R.id.repeatPassword_editText);

        showPass_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // To show the password
                    pass_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    repeatPass_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // To keep the password hidden
                    pass_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    repeatPass_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //-- END Section For ShowPassword Checkbox Functionality --

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });

        //-- BEGIN Section for Test Button Functionality --
        //-- For this activity, the test button will clear all the users in the DB, except the initial admin1 and testUser1
        binding.testButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearUserTable();
                return false;
            }
        });
        //-- END Section for Test Button Functionality --
    }

    /**
     * This method will insert a new user into the USER_TABLE
     */
    private void insertUser() {
        UserInfo user = new UserInfo(mUsername, mPassword, false);
        repository.insertUserInfo(user);
    }

    private void returnToMain() {
        Intent intent = MainActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    private void clearUserTable() {
        repository.clearUsers();
    }

    private boolean userIDAvailable() {
        return !(repository.containsUserName(mUsername));
    }

    /**
     * This method will pull the information from the EditText boxes and save them to their respective local variables
     */
    private void getInformationFromDisplay() {
        mUsername = binding.usernameEditText.getText().toString();
        mPassword = binding.passwordEditText.getText().toString();
        mRepeatPassword = binding.repeatPasswordEditText.getText().toString();
    }

    /**
     * Method to make sure that the user has entered matching passwords
     *
     * @return true if the passwords match, false otherwise
     */
    private boolean password_Validate() {
        if (!mPassword.isEmpty()) {
            return mPassword.equals(mRepeatPassword);
        }
        return false;
    }

    /**
     * Intent Factory for the SignUpActivity
     *
     * @param context The context that the intent factory was called from
     * @return The intent involving this class
     */
    static Intent intentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}