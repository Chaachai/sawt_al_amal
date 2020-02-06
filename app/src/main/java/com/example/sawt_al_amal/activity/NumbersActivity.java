package com.example.sawt_al_amal.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.adapter.CoursListAdapter;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.bean.Niveau;
import com.example.sawt_al_amal.facade.CoursFacade;
import com.example.sawt_al_amal.facade.GesteFacade;
import com.example.sawt_al_amal.facade.NiveauFacade;
import com.example.sawt_al_amal.util.Session;

import java.util.ArrayList;
import java.util.List;

public class NumbersActivity extends AppCompatActivity {

    final Context context = this;

    Button start;

    Button createCours;

    GridView gridView;

    List<Cours> list;

    CoursListAdapter adapter = null;


    GesteFacade mGesteFacade = new GesteFacade(this);

    NiveauFacade niveauFacade = new NiveauFacade(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        final CoursFacade coursFacade = new CoursFacade(this);

        start = findViewById(R.id.start_numbers_btn);
        createCours = findViewById(R.id.createCoursBtn);
        gridView = findViewById(R.id.gridView);

        list = new ArrayList<>();
        adapter = new CoursListAdapter(this, R.layout.cours_items, list);
        gridView.setAdapter(adapter);

        list.clear();

        List<Cours> mCourses = coursFacade.findCoursByLvl(1);
        if (mCourses.isEmpty()) {
            start.setVisibility(View.GONE);
        } else {
            start.setVisibility(View.VISIBLE);
        }
        list.addAll(mCourses);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

        adapter.notifyDataSetChanged();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.updateAttribute(list, "coursList");
                Session.updateAttribute(0, "currentPosition");
                startActivity(new Intent(NumbersActivity.this, GesteActivity.class));
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cours cours = list.get(position);
                Session.updateAttribute(list, "coursList");
                Session.updateAttribute(position, "currentPosition");
                startActivity(new Intent(NumbersActivity.this, GesteActivity.class));
                //System.out.println("COURS ===== " + cours.toString());
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Modifier", "Supprimer"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(NumbersActivity.this);

                dialog.setTitle("choisissez une action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            showDialogUpdate(NumbersActivity.this, list.get(position));
                        } else {
                            // delete
                            showDialogDelete(list.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        createCours.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.create_cours_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView.findViewById(R.id.nomCoursInput);
                final CoursFacade coursFacade = new CoursFacade(getApplicationContext());

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        Niveau niveau = niveauFacade.find(1);
                                        Cours cours = new Cours(userInput.getText().toString(), niveau);
                                        coursFacade.create(cours);
                                        list.add(cours);
                                        adapter.notifyDataSetChanged();
                                        start.setVisibility(View.VISIBLE);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });

    }

    private void showDialogUpdate(Activity activity, final Cours cours) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_cours_activity);
        dialog.setTitle("Modifier");
        final EditText nomCrs = dialog.findViewById(R.id.edtCrs);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        final CoursFacade coursFacade = new CoursFacade(getApplicationContext());

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cours.setNom(nomCrs.getText().toString());
                coursFacade.edit(cours);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Mis à jour avec succés !!!", Toast.LENGTH_SHORT).show();
                updateCoursList();
            }
        });
    }

    private void showDialogDelete(final Cours cours) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(NumbersActivity.this);
        final CoursFacade coursFacade = new CoursFacade(getApplicationContext());
        dialogDelete.setTitle("Attention !!");
        dialogDelete.setMessage("Voulez-vous vraiment supprimer ce cours?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Geste geste = mGesteFacade.findGesteByCours(cours.getId());
                if (geste != null) {
                    mGesteFacade.remove(geste);
                }
                mGesteFacade.remove(geste);
                coursFacade.remove(cours);
                Toast.makeText(getApplicationContext(), "Cours supprimé avec succès!!!", Toast.LENGTH_SHORT)
                        .show();
                updateCoursList();
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

    private void updateCoursList() {
        final CoursFacade coursFacade = new CoursFacade(getApplicationContext());
        list.clear();
        List<Cours> mCourses = coursFacade.findCoursByLvl(1);
        list.addAll(mCourses);
        adapter.notifyDataSetChanged();
    }

}
