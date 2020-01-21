package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sawt_al_amal.R;

public class login extends AppCompatActivity {

    Button btlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btlogin = findViewById(R.id.btlogin);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, HomeActivity.class));
            }
        });
    }
}
