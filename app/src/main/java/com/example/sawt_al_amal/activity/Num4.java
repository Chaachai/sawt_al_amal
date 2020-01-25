package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sawt_al_amal.R;

public class Num4 extends AppCompatActivity {
    Button suiv,prec,list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num4);
        suiv = findViewById(R.id.suiv);
        prec = findViewById(R.id.prec);
        list  = findViewById(R.id.list);
        suiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Num4.this, Num5.class));
            }
        });
        prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Num4.this, Num3.class));
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Num4.this, Numeros.class));
            }
        });
    }
}
