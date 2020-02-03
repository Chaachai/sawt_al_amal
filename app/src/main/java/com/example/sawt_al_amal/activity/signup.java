package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.User;
import com.example.sawt_al_amal.dao.UserDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;
import com.example.sawt_al_amal.util.Validation;
import com.google.android.material.snackbar.Snackbar;

public class signup extends AppCompatActivity {
    EditText username,firstname,lastname,pwd,cpwd,email;
    LinearLayout linearLayout;
    Button register,login;
    Validation validation;
    UserDao userDao;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        validation = new Validation(this);
        userDao=new UserDao(this);
        user = new User();

        login = findViewById(R.id.login);
        register= findViewById(R.id.register);
        username = findViewById(R.id.username);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        cpwd = findViewById(R.id.cpwd);
        linearLayout=findViewById(R.id.linearLayout);

        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        };
        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(signup.this,login.class);
                startActivity(intentRegister);
            }
        };

        login.setOnClickListener(loginListener);
        register.setOnClickListener(registerListener);

    }
    private void post() {
        if (!validation.isInputEditText(username,getString(R.string.error_username))) {
            return;
        }
        if (!validation.isInputEditText(firstname,getString(R.string.error_first_name))) {
            return;
        }
        if (!validation.isInputEditText(lastname,getString(R.string.error_last_name))) {
            return;
        }
        if (!validation.isInputEditText(email,getString(R.string.error_message_email))) {
            return;
        }
        if (!validation.isInputEmail(email,getString(R.string.error_message_email))) {
            return;
        }
        if (!validation.isInputEditText(pwd,getString(R.string.error_message_password))) {
            return;
        }
        if (!validation.isInputMatches(pwd, cpwd, getString(R.string.error_password_match))) {
            return;
        }

        if (!userDao.check(DbStructure.User.C_USERNAME,username.getText().toString().trim())){
            if(!userDao.check(DbStructure.User.C_EMAIL,email.getText().toString().trim())) {
                user.setUsername(username.getText().toString().trim());
                user.setFirstName(firstname.getText().toString().trim());
                user.setLastName(lastname.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());
                user.setPassword(pwd.getText().toString().trim());
                Long res = userDao.create(user);
                if (res == -1)
                {Snackbar.make(linearLayout, getString(R.string.error_message), Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(linearLayout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                    empty();
                }
            }
            else
            {Snackbar.make(linearLayout, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            }

        } else {
            Snackbar.make(linearLayout, getString(R.string.error_user_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    private void empty() {
        username.setText(null);
        firstname.setText(null);
        lastname.setText(null);
        email.setText(null);
        pwd.setText(null);
        cpwd.setText(null);
    }
}
