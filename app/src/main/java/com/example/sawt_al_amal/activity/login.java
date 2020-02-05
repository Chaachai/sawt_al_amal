package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.User;
import com.example.sawt_al_amal.dao.GesteDao;
import com.example.sawt_al_amal.dao.UserDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;
import com.example.sawt_al_amal.util.Session;
import com.example.sawt_al_amal.util.Validation;
import com.google.android.material.snackbar.Snackbar;

public class login extends AppCompatActivity {

    Button btlogin,btlogingoogle,register;
    EditText username,pwd;
    UserDao userDao;
    LinearLayout linearLayout;
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        validation = new Validation(this);
        userDao=new UserDao(this);
        btlogin = findViewById(R.id.btlogin);
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
        register= findViewById(R.id.register);
        btlogingoogle = findViewById(R.id.btlogingoogle);
        linearLayout=findViewById(R.id.linearLayout);
        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        };
        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(login.this,signup.class);
                startActivity(intentRegister);
            }
        };
        btlogin.setOnClickListener(loginListener);
        register.setOnClickListener(registerListener);
        btlogingoogle.setOnClickListener(googleListener);


    }

    View.OnClickListener googleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            google();
        }
    };
    public void google() {
       /* Cursor res = userDao.all();
        if(res.getCount() == 0) {
            Toast.makeText(login.this,"لا توجد اقتراحات ",Toast.LENGTH_LONG).show();
        }
        while (res.moveToNext()) {
            Toast.makeText(login.this,res.getString(3)+"\n"+res.getString(4)+"\n",Toast.LENGTH_LONG).show();
        }*/

    }
    public void verify() {
        if (!validation.isInputEditText(username, getString(R.string.error_username))) {
            return;
        }
        if (!validation.isInputEditText(pwd, getString(R.string.error_message_password))) {
            return;
        }
        //User user= new User();
        Cursor res =userDao.checklogin(username.getText().toString().trim(),pwd.getText().toString().trim());
        if (res.getCount() != 0) {
            Session.updateAttribute(username.getText().toString(), "connectedUser");
            Intent intent = new Intent(login.this,HomeActivity.class);
            //intent.putExtra("user", username.getText().toString().trim());
            //empty();
            startActivity(intent);
            finish();
        }
        else {
            //Toast.makeText(login.this,R.string.error_valid_email_password,Toast.LENGTH_LONG).show();
            Snackbar.make(linearLayout,getString(R.string.error_valid_user_password), Snackbar.LENGTH_LONG).show();
        }
    }
    private void empty() {
        username.setText(null);
        pwd.setText(null);
    }
}
