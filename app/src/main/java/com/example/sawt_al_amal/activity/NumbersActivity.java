package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sawt_al_amal.R;

public class NumbersActivity extends AppCompatActivity {

    Button start_numbers_btn;

    Button zero_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        start_numbers_btn = findViewById(R.id.start_numbers_btn);
        zero_btn = findViewById(R.id.btn0);

        start_numbers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NumbersActivity.this, NumZeroActivity.class));
            }
        });

        zero_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NumbersActivity.this, NumZeroActivity.class));
            }
        });
    }
}
