package com.example.sawt_al_amal.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.dao.GesteDao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/* AZALMAD Ilham */
public class SpeechActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button speech;
    private TextView textv;
    private EditText textadd;
    private String resultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);


        View.OnClickListener speechListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speech();
            }
        };


        textv =findViewById(R.id.textv);
        //textadd =findViewById(R.id.textadd);
        speech =findViewById(R.id.speech);
        speech.setOnClickListener(speechListener);




    }

    // intent qui déclanche l'activité de reconnaissance vocale
    private void Speech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-MA");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Vous pouvez parler ...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Désolé, votre appareil ne supporte pas d'entrée vocale...", Toast.LENGTH_SHORT).show();
        }
    }

    // fonction qui récupere le text retourner par l'intent ACTION_RECOGNIZE_SPEECH
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> buffer = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultat=buffer.get(0);
                    textv.setText(resultat);
                    search(resultat);
                }
                break;
            }
            default: break;
        }
    }
    public  void search(String result){
        Intent intent=new Intent(SpeechActivity.this,DisplayActivity.class);
        intent.putExtra("mots",result);
        startActivity(intent);

    }


    }
