package com.example.sawt_al_amal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.facade.CategoryFacade;
import com.example.sawt_al_amal.facade.CoursFacade;
import com.example.sawt_al_amal.facade.GesteFacade;
import com.example.sawt_al_amal.util.FileUtils;
import com.example.sawt_al_amal.util.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class CreateGeste extends AppCompatActivity {
    Button choose;
    Button insert;
    GifImageView gifImageView;
    EditText geste_text;
    EditText id_category;

    private byte[] b = null;

    final int REQUEST_CODE_GALLERY = 999;

    GesteFacade gesteFacade = new GesteFacade(this);
    CoursFacade coursFacade = new CoursFacade(this);
    CategoryFacade categoryFacade = new CategoryFacade(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_geste);

        choose = findViewById(R.id.choose);
        insert = findViewById(R.id.insert);
        gifImageView = findViewById(R.id.mygif);
        geste_text = findViewById(R.id.designationGeste);
        id_category = findViewById(R.id.id_category);



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        CreateGeste.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b == null) {
                    Toast.makeText(getApplicationContext(), "5tar chi GIF !!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Geste geste = new Geste();
                        int idCours = (int) Session.getAttribut("id_cours");
                        Cours cours = coursFacade.find(idCours);
                        Category category = categoryFacade.find(id_category.getText());
                        if(cours == null || category == null){
                            Toast.makeText(getApplicationContext(), "Cours ola category makayninch", Toast.LENGTH_SHORT).show();
                        }else{
                            geste.setGif(b);
                            geste.setText(geste_text.getText().toString());
                            geste.setCours(cours);
                            geste.setCategory(category);
                            gesteFacade.create(geste);
                            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateGeste.this, GesteActivity.class));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String path = FileUtils.getPath(this, uri);
            File file = new File(path);
            b = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
                for (int i = 0; i < b.length; i++) {
                    //System.out.print("********* " + (char) b[i]);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
            } catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
            }

            try {
                GifDrawable gif = new GifDrawable(b);
                gifImageView.setVisibility(View.VISIBLE);
                gifImageView.setImageDrawable(gif);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
