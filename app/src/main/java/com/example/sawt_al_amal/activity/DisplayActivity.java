package com.example.sawt_al_amal.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.dao.GesteDao;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class DisplayActivity extends AppCompatActivity {

    private ListView listView;

    String text[] ;

    byte[] gif[];
    String mots[];
    public static final String nom="mots";
    String result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        result = intent.getStringExtra(nom);
        search(result);
    }
       public  void search(String result){
            final String SEPARATEUR = " ";
           GesteDao ud =new GesteDao(this);
           String mots[] = result.split(SEPARATEUR);
            int j=-1;
            int nbr=0;
            for (int i = 0; i < mots.length; i++) {
                Cursor res = ud.getAllData(mots[i]);
                if(res.getCount() == 0) {
                    Toast.makeText(DisplayActivity.this,"لا توجد أي اقتراحات, المرجو إعادة المحاولة",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(DisplayActivity.this,SpeechActivity.class);
                    startActivity(intent);
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

                }
            }
           MyAdapter adapter = new MyAdapter(this, text, gif);
           listView.setAdapter(adapter);
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
}