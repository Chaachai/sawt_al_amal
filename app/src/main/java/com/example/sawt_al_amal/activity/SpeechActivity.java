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

public class SpeechActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button speech,ok;
    private TextView textv;
    private EditText textadd;
    private String resultat;
    private ListView listView;
    String text[]={};
    byte[] gif[]={};

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
        View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(textv.getText().toString());
            }
        };


        listView = findViewById(R.id.listView);
        textv =findViewById(R.id.textv);
        //textadd =findViewById(R.id.textadd);
        speech =findViewById(R.id.speech);
        speech.setOnClickListener(speechListener);
        ok =findViewById(R.id.ok);
        ok.setOnClickListener(okListener);



    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> buffer = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultat=buffer.get(0);
                    textv.setText(resultat);
                    ok.setVisibility(View.VISIBLE);
                }
                break;
            }
            default: break;
        }
    }

    public  void search(String result){
        final String SEPARATEUR = " ";
        List<Geste> list;
        GesteDao ud =new GesteDao(this);
        String mots[] = result.split(SEPARATEUR);
        //textv.setText("\n");
        int j=-1;
        int nbr=0;
        for (int i = 0; i < mots.length; i++) {
             /*String contraint =DbStructure.Geste.C_TEXT  + " LIKE '%" + mots[i] + "%'";
             Toast.makeText(SpeechActivity.this,contraint,Toast.LENGTH_LONG).show();
            list=ud.loadAll(contraint);
           Toast.makeText(SpeechActivity.this,"1",Toast.LENGTH_LONG).show();
            for(int j = 0; j<list.size();j++)
            {
                textv.append(list.get(i).getText());
        }
        }*/
            Cursor res = ud.getAllData(mots[i]);
            if(res.getCount() == 0) {
            }
            while (res.moveToNext()) {
                nbr++;
            }
        }
        text = new String[nbr];
        gif = new byte[nbr][];
        for (int i = 0; i < mots.length; i++) {
            Cursor res = ud.getAllData(mots[i]);
            if(res.getCount() == 0) {
            }
            while (res.moveToNext()) {
                j++;
                text[j]=res.getString(3);
                gif[j]=res.getBlob(1);
                MyAdapter adapter = new MyAdapter(this, text, gif);
                listView.setAdapter(adapter);
            }
        }
    }
    public  void insert(String txt){
        Cours cours=new Cours();
        Geste geste=new Geste();
        geste.setText(txt);
        geste.setCours(cours);
        GesteDao ud =new GesteDao(this);
        Long res=ud.create(geste);
        if(res == -1)
            Toast.makeText(SpeechActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(SpeechActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rtitre[];
        byte[] rgif[];

        MyAdapter (Context c, String text[], byte[] gif[]) {
            super(c, R.layout.row, text);
            this.context = c;
            this.rtitre = text;
            this.rgif = gif;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row, parent, false);

            GifImageView gif = row.findViewById(R.id.gif);
            TextView text = row.findViewById(R.id.textView1);

            try {
                gif.setImageDrawable(new GifDrawable(rgif[position]));
                text.setText(rtitre[position]);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return row;
        }
    }

/*
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }*/


}
