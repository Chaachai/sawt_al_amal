package com.example.sawt_al_amal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.MainActivity2;
import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.bean.Niveau;
import com.example.sawt_al_amal.facade.CategoryFacade;
import com.example.sawt_al_amal.facade.NiveauFacade;
import com.example.sawt_al_amal.util.Session;
import java.util.List;
//HOUSSEINI Abbalele Ibrhim
public class HomeActivity extends AppCompatActivity {

    CardView cam_cv;

    CategoryFacade categoryFacade;

    CardView commun_cv;

    CardView lessons_cv;

    CardView logout_cv;

    NiveauFacade niveauFacade ;

    CardView noise_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        niveauFacade = new NiveauFacade(this);
        categoryFacade = new CategoryFacade(this);
        List<Category> categories = categoryFacade.findAll();
        List<Niveau> niveaux = niveauFacade.findAll();

        if (categories.size() == 0) {
            categoryFacade.create(new Category("category 1"));
            categoryFacade.create(new Category("category 2"));
            categoryFacade.create(new Category("category 3"));
            categoryFacade.create(new Category("category 4"));
            categoryFacade.create(new Category("category 5"));
            categoryFacade.create(new Category("category 6"));
        }

        if (niveaux.size() == 0) {
            niveauFacade.create(new Niveau("المستوى الأول", "تعلم الأرقام والأعداد ", 0));
            niveauFacade.create(new Niveau("المستوى الثاني", "تعلم الحروف الابجدية ", 20));
            niveauFacade.create(new Niveau("المستوى الثالث", "أيام الأسبوع ", 30));
            niveauFacade.create(new Niveau("المستوى الرابع", "تعلم الألوان ", 40));
            niveauFacade.create(new Niveau("المستوى الخامس", "الوقت ", 50));
        }

        lessons_cv = findViewById(R.id.lessons_cv);
        noise_cv = findViewById(R.id.noise_cv);
        commun_cv = findViewById(R.id.commun_cv);
        logout_cv = findViewById(R.id.logout_cv);
        cam_cv = findViewById(R.id.cam_cv);

        lessons_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LevelsActivity.class));
            }
        });

        noise_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity2.class));
            }
        });
        commun_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SpeechActivity.class));

            }
        });

        cam_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity3.class));
            }
        });
        logout_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity1.class));
                Session.clear();
                finish();

            }
        });

    }
}
