package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.activity.soundRec.vr.record.MainActivity2;

public class HomeActivity extends AppCompatActivity {

    CardView lessons_cv;
    CardView noise_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lessons_cv = findViewById(R.id.lessons_cv);
        noise_cv = findViewById(R.id.noise_cv);

        lessons_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LessonsActivity.class));
            }
        });

        noise_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity2.class));
            }
        });
    }
}
