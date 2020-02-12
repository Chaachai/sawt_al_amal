package com.example.sawt_al_amal.activity.apiSrecog.vr.record;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.Recognizer.ResultsManager;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.list.RecordingsDatabase;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.list.RecordingsListActivity;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.recorder.ContinuousRecordingService;
import com.example.sawt_al_amal.activity.apiSrecog.vr.record.recorder.RecordActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//HALI Hossam
//l'activity correspod a l'interface du detection du bruit
public class MainActivity2 extends AppCompatActivity {

    private static final int REQ_CODE_EDIT = 1;

    private static boolean isRecording = false;

    private static MainActivity2 contextRecognitionHandler;

    ImageView img;

    ProgressDialog progress;

    private Intent recorder;

    private Button startRecognitionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity2.this, RecordActivity.class), REQ_CODE_EDIT);
            }
        });

        final View testing = findViewById(R.id.testing);
        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Testing.class));
            }
        });

        final TextView recognizedSoundLabel = (TextView) findViewById(R.id.recognized_sound);
        ResultsManager.setTextViewReference(recognizedSoundLabel);
        img = findViewById(R.id.logo);
        ResultsManager.setImage(img);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ResultsManager.setVibrator(v);

        final Button recordingsListButton = (Button) findViewById(R.id.recordings_list);
        final FloatingActionButton rightRecordButton = (FloatingActionButton) findViewById(R.id.fab);

        contextRecognitionHandler = this;
        startRecognitionButton = (Button) findViewById(R.id.start_recognition);
        startRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isRecording) {
                    startRecognitionButton.setText("توقف التعرف");
                    isRecording = true;
                    progress = ProgressDialog.show(MainActivity2.this, "بدء التعرف على الصوت",
                            "الرجاء الانتظار أثناء تحميل أنماط الصوت من قائمة التسجيلات الخاصة بك ...", true);
                    recordingsListButton.setVisibility(View.INVISIBLE);
                    rightRecordButton.setVisibility(View.INVISIBLE);
                    ResultsManager.setProgressBar(progress);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            recorder = new Intent(contextRecognitionHandler, ContinuousRecordingService.class);
                            startService(recorder);
                        }
                    }).start();

                } else {
                    startRecognitionButton.setText("بدء التعرف");
                    isRecording = false;
                    stopService(recorder);
                    String uri = "@drawable/logo";  // where myresource (without the extension) is the file

                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                    Drawable res = getResources().getDrawable(imageResource);
                    img.setImageDrawable(res);
                    recognizedSoundLabel.setText("");
                    recordingsListButton.setVisibility(View.VISIBLE);
                    rightRecordButton.setVisibility(View.VISIBLE);
                }
            }
        });

        setupRecordingsListVisibility();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_EDIT && resultCode == RESULT_OK) {
            setupRecordingsListVisibility();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME || (keyCode == KeyEvent.KEYCODE_BACK)) {
            stop();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_licenses) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecordingsListVisibility() {
        final View recordingsList = findViewById(R.id.recordings_list);

        final RecordingsDatabase recordingsDatabase = new RecordingsDatabase(this);
        if (recordingsDatabase.getAllRecordings().size() > 0) {
            recordingsList.setVisibility(View.VISIBLE);
            recordingsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(MainActivity2.this, RecordingsListActivity.class),
                            REQ_CODE_EDIT);
                }
            });
        } else {
            recordingsList.setVisibility(View.GONE);
        }
    }

    private void stop() {
        if (isRecording) {
            startRecognitionButton.setText("Start recognition");
            isRecording = false;
            stopService(recorder);
        }
    }
}
