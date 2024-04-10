package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eggert_hoppens_project2.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.categoryFirstOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameModeActivity.intentFactory(
                        CategoryActivity.this,
                        binding.categoryFirstOptionButton.getText().toString());
                startActivity(intent);
            }
        });

        binding.categorySecondOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameModeActivity.intentFactory(
                        CategoryActivity.this,
                        binding.categorySecondOptionButton.getText().toString());
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context){
         return new Intent(context, CategoryActivity.class);
    }
}