package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sawt_al_amal.R;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btSigIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = findViewById(R.id.btlogin);
        btSigIn = findViewById(R.id.btsignup);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btlogin:
                        startActivity(new Intent(MainActivity.this, login.class));
                        break;
                    default:
                        break;

                }
            }
        });
        btSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btsignup:
                        startActivity(new Intent(MainActivity.this, signup.class));
                        break;
                    default:
                        break;

                }
            }
        });
    }


}

