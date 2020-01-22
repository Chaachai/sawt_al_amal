package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.facade.UserFacade;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btSigIn;

    UserFacade userFacade = new UserFacade(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userFacade.open();
        userFacade.close();


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

