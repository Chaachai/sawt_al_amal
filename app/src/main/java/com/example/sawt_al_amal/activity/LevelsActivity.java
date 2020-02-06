package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sawt_al_amal.R;

//HOUSSEINI Abbalele Ibrhim

public class LevelsActivity extends AppCompatActivity {

    CardView numbers_cv;

    CardView alphabet_cv;
    CardView days_cv;
    CardView colors_cv;
    CardView time_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

//

        numbers_cv = findViewById(R.id.numbers_cv);
        alphabet_cv = findViewById(R.id.alphabet_cv);
        days_cv = findViewById(R.id.days_cv);
        colors_cv = findViewById(R.id.colors_cv);
        time_cv = findViewById(R.id.time_cv);

        numbers_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, NumbersActivity.class));
            }
        });

        alphabet_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, AlphabetActivity.class));
            }
        });

        days_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, DaysActivity.class));
            }
        });

        colors_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, ColorsActivity.class));
            }
        });

        time_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, TimeActivity.class));
            }
        });


    }
}
