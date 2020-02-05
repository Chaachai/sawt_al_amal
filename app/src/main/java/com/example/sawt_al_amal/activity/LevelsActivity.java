package com.example.sawt_al_amal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sawt_al_amal.R;

public class LevelsActivity extends AppCompatActivity {

    CardView numbers_cv;

    CardView alphabet_cv;
    CardView days_cv;
    CardView colors_cv;
    CardView time_cv;

//    CoursFacade coursFacade = new CoursFacade(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

//        List<Cours> cours = coursFacade.findAll();

//        if (cours.size() == 0) {
//            coursFacade.create(new Cours())
//        }
//

//        categoryFacade.create(new Category("TEST 1"));
//        categoryFacade.create(new Category( "TEST 2"));

//        Niveau niveau1 = niveauFacade.find(1);
//        if (niveau1 == null)
//            System.out.println("nuuuuuuul ");
//        Niveau niveau2 = new Niveau();
//
//        niveau1.setDescription("desc test haha");
//        niveau1.setNom("Niveau 1");
//
//        niveau2.setDescription("desc2 aaaa");
//        niveau2.setNom("Niveau 2");
//
//        niveauFacade.create(niveau1);
//        niveauFacade.create(niveau2);

//
//        Cours c1 = new Cours();
//        Cours c2 = new Cours();
//        Cours c3 = new Cours();
//        Cours c4 = new Cours();
//
//
//        c1.setNom("cours 1");
//        c2.setNom("cours 2");
//        c3.setNom("cours 3");
//        c4.setNom("cours 4");
//
//        c1.setNiveau(niveau1);
//        c2.setNiveau(niveau1);
//        c3.setNiveau(niveau1);
//        c4.setNiveau(niveau1);
//
//        coursFacade.create(c1);
//        coursFacade.create(c2);
//        coursFacade.create(c3);
//        coursFacade.create(c4);
//

        numbers_cv = findViewById(R.id.numbers_cv);
        alphabet_cv = findViewById(R.id.alphabet_cv);
        days_cv = findViewById(R.id.days_cv);
        colors_cv = findViewById(R.id.colors_cv);
        time_cv = findViewById(R.id.time_cv);

        numbers_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, NumbersActivity.class));
            }
        });

        alphabet_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, AlphabetActivity.class));
            }
        });

        days_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, DaysActivity.class));
            }
        });

        colors_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, ColorsActivity.class));
            }
        });

        time_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this, TimeActivity.class));
            }
        });


    }
}
