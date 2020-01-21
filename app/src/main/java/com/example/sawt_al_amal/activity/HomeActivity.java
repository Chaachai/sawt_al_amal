package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sawt_al_amal.R;

public class HomeActivity extends AppCompatActivity {


    CardView lessons_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lessons_cv = findViewById(R.id.lessons_cv);

        lessons_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LessonsActivity.class));
            }
        });
    }
}
