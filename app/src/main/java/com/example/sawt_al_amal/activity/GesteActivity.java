package com.example.sawt_al_amal.activity;

import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
//CHAACHAI Youssef
public class GesteActivity extends AppCompatActivity {

    TextView geste_text;

    GifImageView geste_gif;

    ImageView geste_image;

    Button createGeste;

    Button editGeste;

    Button deleteGeste;

    LinearLayout edit_delete_layout;

    Button next;

    Button back;

    Button menu;

    GesteFacade gesteFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geste);
        gesteFacade = new GesteFacade(this);

        final List<Cours> coursList = (List<Cours>) Session.getAttribut("coursList");
        int pos = (int) Session.getAttribut("currentPosition");
        final Cours cours = coursList.get(pos);

        System.out.println("Position ===================== " + pos);
        System.out.println("Cours ===================== " + cours.toString());

        createGeste = findViewById(R.id.createGeste);
        geste_text = findViewById(R.id.geste_text);
        geste_gif = findViewById(R.id.geste_gif);
        geste_image = findViewById(R.id.geste_image);
        edit_delete_layout = findViewById(R.id.edit_delete_layout);
        deleteGeste = findViewById(R.id.delete_geste);
        editGeste = findViewById(R.id.edit_geste);

        next = findViewById(R.id.next_btn);
        back = findViewById(R.id.back_btn);
        menu = findViewById(R.id.menu_btn);


        String user = (String) Session.getAttribut("connectedUser");

        System.out.println("============================== "+user);
        if (!user.trim().equals("chaachai")) {
            System.out.println("============= 00000000000  ================= ");
//            edit_delete_layout.setVisibility(View.);
            editGeste.setVisibility(View.GONE);
            deleteGeste.setVisibility(View.GONE);
            createGeste.setVisibility(View.GONE);
        } else {
            System.out.println("============= 11111111111  ================= ");
            editGeste.setVisibility(View.VISIBLE);
            deleteGeste.setVisibility(View.VISIBLE);
            createGeste.setVisibility(View.VISIBLE);

        }

        editGeste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                Geste geste = gesteFacade.findGesteByCours(cours.getId());
                Session.updateAttribute(geste, "ediGeste");
                startActivity(new Intent(GesteActivity.this, EditGeste.class));
            }
        });

        deleteGeste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                Geste geste = gesteFacade.findGesteByCours(cours.getId());
                showDialogDelete(geste);
            }
        });

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
//                int lvl = cours.getNiveau().getId();
//                switch (lvl) {
//                    case 1:
//                        startActivity(new Intent(GesteActivity.this, NumbersActivity.class));
//                        finish();
//                        break;
//                    case 2:
//                        startActivity(new Intent(GesteActivity.this, AlphabetActivity.class));
//                        finish();
//                        break;
//                    case 3:
//                        startActivity(new Intent(GesteActivity.this, DaysActivity.class));
//                        finish();
//                        break;
//                    case 4:
//                        startActivity(new Intent(GesteActivity.this, ColorsActivity.class));
//                        finish();
//                        break;
//                    case 5:
//                        startActivity(new Intent(GesteActivity.this, TimeActivity.class));
//                        finish();
//                        break;
//                    default:
//                        startActivity(new Intent(GesteActivity.this, LevelsActivity.class));
//                        finish();
//                }
                finish();

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
                edit_delete_layout.setVisibility(View.VISIBLE);
                createGeste.setVisibility(View.GONE);
                geste_text.setText(geste.getText());
                if (geste.getImage() != null) {
                    byte[] gesteImage = geste.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(gesteImage, 0, gesteImage.length);
                    geste_image.setImageBitmap(bitmap);
                } else {
                    geste_image.setVisibility(View.GONE);
                }
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
                geste_image.setVisibility(View.GONE);
                edit_delete_layout.setVisibility(View.GONE);
            }

        } else {
            geste_text.setText("");
            geste_gif.setVisibility(View.GONE);
            geste_image.setVisibility(View.GONE);
            edit_delete_layout.setVisibility(View.GONE);
        }


    }

    //Affichage d'un popup pour la suppression
    private void showDialogDelete(final Geste geste) {
//        final GesteFacade gesteFacade = new GesteFacade(this);
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(GesteActivity.this);

        dialogDelete.setTitle("Attention !!");
        dialogDelete.setMessage("Voulez-vous vraiment supprimer ce geste?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gesteFacade.remove(geste);
                Toast.makeText(getApplicationContext(), "Geste supprimé avec succés ", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });

        dialogDelete.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
}
