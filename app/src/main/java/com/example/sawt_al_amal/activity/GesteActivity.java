package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.facade.GesteFacade;
import com.example.sawt_al_amal.util.Session;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GesteActivity extends AppCompatActivity {

    TextView geste_text;
    GifImageView geste_gif;
    Button createGeste;
    GesteFacade gesteFacade = new GesteFacade(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geste);

        createGeste = findViewById(R.id.createGeste);
        geste_text = findViewById(R.id.geste_text);
        geste_gif = findViewById(R.id.geste_gif);

        createGeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GesteActivity.this, CreateGeste.class));
            }
        });

        Cours cours = (Cours) Session.getAttribut("selectedCours");

        if (cours != null) {
//            System.out.println(cours.toString());
            Geste geste = gesteFacade.findGesteByCours(cours.getId());
            if (geste != null) {
                geste_text.setText(geste.getText());
                try {
                    geste_gif.setImageDrawable(new GifDrawable(geste.getGif()));
                    geste_gif.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                geste_text.setText("");
                geste_gif.setVisibility(View.GONE);
            }

        } else {
            geste_text.setText("");
            geste_gif.setVisibility(View.GONE);
        }


    }
}
