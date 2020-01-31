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
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GesteActivity extends AppCompatActivity {

    TextView geste_text;
    GifImageView geste_gif;
    Button createGeste;
    Button next;
    Button back;
    Button menu;
    GesteFacade gesteFacade = new GesteFacade(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geste);

        final List<Cours> coursList = (List<Cours>) Session.getAttribut("coursList");
        int pos = (int) Session.getAttribut("currentPosition");
        final Cours cours = coursList.get(pos);

        System.out.println("Position ===================== " + pos);
        System.out.println("Cours ===================== " + cours.toString());

        createGeste = findViewById(R.id.createGeste);
        geste_text = findViewById(R.id.geste_text);
        geste_gif = findViewById(R.id.geste_gif);

        next = findViewById(R.id.next_btn);
        back = findViewById(R.id.back_btn);
        menu = findViewById(R.id.menu_btn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) Session.getAttribut("currentPosition");
                if (pos < coursList.size() - 1) {
                    pos++;
                    Session.updateAttribute(pos, "currentPosition");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) Session.getAttribut("currentPosition");
                if (pos != 0) {
                    pos--;
                    Session.updateAttribute(pos, "currentPosition");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GesteActivity.this, NumbersActivity.class));
            }
        });

        createGeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.updateAttribute(cours.getId(), "id_cours");
                startActivity(new Intent(GesteActivity.this, CreateGeste.class));
            }
        });


        if (cours != null) {
//            System.out.println(cours.toString());
            Geste geste = gesteFacade.findGesteByCours(cours.getId());
            if (geste != null) {
                createGeste.setVisibility(View.GONE);
                geste_text.setText(geste.getText());
                try {
                    geste_gif.setImageDrawable(new GifDrawable(geste.getGif()));
                    geste_gif.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                createGeste.setVisibility(View.VISIBLE);
                geste_text.setText("");
                geste_gif.setVisibility(View.GONE);
            }

        } else {
            geste_text.setText("");
            geste_gif.setVisibility(View.GONE);
        }


    }
}
