package com.example.sawt_al_amal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.facade.CategoryFacade;
import com.example.sawt_al_amal.facade.CoursFacade;
import com.example.sawt_al_amal.facade.GesteFacade;
import com.example.sawt_al_amal.facade.NiveauFacade;
import com.example.sawt_al_amal.util.FileUtils;
import com.example.sawt_al_amal.util.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
//l'interface admin pour creer les gestes
//CHAACHAI Youssef
public class CreateGeste extends AppCompatActivity {
    Button choose;
    Button choose2;
    Button insert;
    GifImageView gifImageView;
    ImageView imageView;
    EditText geste_text;
    EditText id_category;

    private byte[] b = null;

    final int REQUEST_CODE_GALLERY = 999;
    final int REQUEST_CODE_IMAGE = 777;

    GesteFacade gesteFacade ;
    CoursFacade coursFacade ;
    CategoryFacade categoryFacade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_geste);

        coursFacade = new CoursFacade(this);
        gesteFacade = new GesteFacade(this);
        categoryFacade = new CategoryFacade(this);

        choose = findViewById(R.id.choose);
        choose2 = findViewById(R.id.choose2);
        insert = findViewById(R.id.insert);
        gifImageView = findViewById(R.id.mygif);
        imageView = findViewById(R.id.myImage);
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

        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        CreateGeste.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_IMAGE
                );
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si aucune image est chargé
                if (b == null || imageViewToByte(imageView) == null) {
                    Toast.makeText(getApplicationContext(), "GIF ola IMAGE null !!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Geste geste = new Geste();
                        int idCours = (int) Session.getAttribut("id_cours");
                        Cours cours = coursFacade.find(idCours);
                        Category category = categoryFacade.find(1);
                        if (cours == null || category == null) {
                            Toast.makeText(getApplicationContext(), "Cours ola category makayninch", Toast.LENGTH_SHORT).show();
                        } else {
                            geste.setGif(b);
                            geste.setText(geste_text.getText().toString());
                            geste.setCours(cours);
                            geste.setCategory(category);
                            geste.setImage(imageViewToByte(imageView));
                            gesteFacade.create(geste);
                            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateGeste.this, GesteActivity.class));
                            finish();
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
        //l'utilisateur a choisi un GIF
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/gif");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
            //l'utilisateur a choisi une IMAGE
        } else if (requestCode == REQUEST_CODE_IMAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
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


        } else if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
//onvertir image de bitmap aen byte
    public static byte[] imageViewToByte(ImageView image) {
        if (image == null) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
