package com.example.sawt_al_amal.activity.soundRec.vr.record;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.sawt_al_amal.R;

public class Licenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
        WebView w= (WebView)findViewById(R.id.webViewLicenses);
        w.loadUrl("file:///android_asset/licenses.html");
    }
}
