package com.example.sawt_al_amal.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.facade.CoursFacade;
import com.example.sawt_al_amal.facade.GesteFacade;
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

public class EditGeste extends AppCompatActivity {

    Button edt_choose;

    Button edt_choose2;

    Button edt_insert;

    GifImageView edt_gifImageView;

    ImageView edt_imageView;

    EditText edt_geste_text;

    private byte[] bytes;

    final int REQUEST_CODE_GALLERY = 999;

    final int REQUEST_CODE_IMAGE = 777;

    GesteFacade gesteFacade ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_geste);

        gesteFacade = new GesteFacade(this);

        edt_choose = findViewById(R.id.edt_choose);
        edt_choose2 = findViewById(R.id.edt_choose2);
        edt_insert = findViewById(R.id.edt_insert);
        edt_gifImageView = findViewById(R.id.edt_mygif);
        edt_imageView = findViewById(R.id.edt_myImage);
        edt_geste_text = findViewById(R.id.edt_designationGeste);

        final Geste geste = (Geste) Session.getAttribut("ediGeste");
        if (geste != null) {
            if (geste.getGif() != null) {
                bytes = geste.getGif();
                try {
                    edt_gifImageView.setImageDrawable(new GifDrawable(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (geste.getImage() != null) {
                byte[] gesteImage = geste.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(gesteImage, 0, gesteImage.length);
                edt_imageView.setImageBitmap(bitmap);
            }
            if (geste.getText() != null) {
                edt_geste_text.setText(geste.getText());
            }
        }

        edt_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityCompat.requestPermissions(
                        EditGeste.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        edt_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditGeste.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_IMAGE
                );
            }
        });

        edt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bytes == null || imageViewToByte(edt_imageView) == null) {
                    Toast.makeText(getApplicationContext(), "GIF ola IMAGE null !!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        geste.setGif(bytes);
                        geste.setText(edt_geste_text.getText().toString());
                        geste.setImage(imageViewToByte(edt_imageView));
                        gesteFacade.edit(geste);
                        Toast.makeText(getApplicationContext(), "Gest modifié avec succès !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditGeste.this, GesteActivity.class));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/gif");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!",
                        Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_IMAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!",
                        Toast.LENGTH_SHORT).show();
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
            bytes = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
                for (int i = 0; i < bytes.length; i++) {
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
                GifDrawable gif = new GifDrawable(bytes);
                edt_gifImageView.setImageDrawable(gif);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                edt_imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

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
