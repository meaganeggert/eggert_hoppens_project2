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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.databinding.ActivityMainBinding;
import com.example.eggert_hoppens_project2.databinding.ActivitySignUpBinding;

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

        repository = AppRepository.getRepository(getApplication());

        //-- BEGIN Section for Sign Up Button Functionality --
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                if (password_Validate()){
                    Toast.makeText(getApplicationContext(), "Passwords MATCH", Toast.LENGTH_SHORT).show();
                    insertUser();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords must match!", Toast.LENGTH_SHORT).show();
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
    }

    /**
     * This method will insert a new user into the USER_TABLE
     */
    private void insertUser() {
        UserInfo user = new UserInfo(mUsername, mPassword, false);
        repository.insertUserInfo(user);
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
     * @return true if the passwords match, false otherwise
     */
    private boolean password_Validate() {
        return mPassword.equals(mRepeatPassword);
    }

    /**
     * Intent Factory for the SignUpActivity
     * @param context The context that the intent factory was called from
     * @return The intent involving this class
     */
    static Intent intentFactory(Context context){
        return new Intent(context, SignUpActivity.class);
    }
}