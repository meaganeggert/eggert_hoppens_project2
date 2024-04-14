package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggert_hoppens_project2.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Upon selecting the first category option, use the intent factory from the GameModeActivity
        //and pass an extra for the selected category's name.
        binding.categoryFirstOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = binding.categoryFirstOptionButton.getText().toString();
                Intent intent = GameModeActivity.intentFactory(
                        CategoryActivity.this,
                        categoryName);
                startActivity(intent);
            }
        });

        //Upon selecting the second category option, use the intent factory from the GameModeActivity
        //and pass an extra for the selected category's name.
        binding.categorySecondOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String categoryName = binding.categorySecondOptionButton.getText().toString();
//                Intent intent = GameModeActivity.intentFactory(
//                        CategoryActivity.this,
//                        categoryName);
//                startActivity(intent);
                Toast.makeText(CategoryActivity.this, "Sports category not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * CategoryActivity intent factory intended to be used within the LoginActivity (which is
     * actually the main activity). Utilizes the context of the previous activity (LoginActivity).
     * @param context Context of the previous activity (LoginActivity)
     * @return Returns intent to be used to start CategoryActivity
     */
    public static Intent intentFactory(Context context){
         return new Intent(context, CategoryActivity.class);
    }
}