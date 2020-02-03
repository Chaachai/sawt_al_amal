package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.sawt_al_amal.R;

public class DisplayActivity extends AppCompatActivity {
    private ListView listView;
    String text[]={};
    byte[] gif[]={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView = findViewById(R.id.listView);

    }
}
