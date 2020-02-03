package com.example.sawt_al_amal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sawt_al_amal.R;

public class NumZeroActivity extends AppCompatActivity {

    Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_zero);

        menu_btn = findViewById(R.id.menu_btn);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NumZeroActivity.this, NumbersActivity.class));
            }
        });
    }
}
