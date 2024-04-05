package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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

import com.example.eggert_hoppens_project2.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    public static final String TAG = "EGGHOP_MAIN";
    CheckBox showPass_checkBox;
    EditText pass_editText;

    String mUsername = "testUser";
    String mPassword = "testPass";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // This will only be used while debugging
        binding.testResultTextView.setMovementMethod(new ScrollingMovementMethod());


        //-- BEGIN Sign-Up Button Functionality --
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.intentFactory(MainActivity.this);
                startActivity(intent);

            }
        });
        //-- END Sign-Up Button Functionality --

        //-- BEGIN Section for Test Button Functionality --
        binding.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                updateDisplay();
            }
        });
        //-- END Section for Test Button Functionality --

        //-- BEGIN Section For ShowPassword Checkbox Functionality --
        showPass_checkBox = findViewById(R.id.showPassword_checkBox);
        pass_editText = findViewById(R.id.password_editText);

        showPass_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // To show the password
                    pass_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // To keep the password hidden
                    pass_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //-- END Section For ShowPassword Checkbox Functionality --

    }

    static Intent intentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }

    private void updateDisplay() {
        String currentInfo = binding.testResultTextView.getText().toString();
        String newDisplay = String.format(Locale.ROOT, "Username: %s%nPassword: %s%n-=-=-=-=-=-=-%n%s%n", mUsername, mPassword, currentInfo);
        binding.testResultTextView.setText(newDisplay);

    }
    private void getInformationFromDisplay() {
        mUsername = binding.usernameEditText.getText().toString();
        mPassword = binding.passwordEditText.getText().toString();
    }
}