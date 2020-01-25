package com.example.sawt_al_amal.activity.soundRec.vr.record;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.activity.soundRec.vr.record.Recognizer.ResultsManager;
import com.example.sawt_al_amal.activity.soundRec.vr.record.list.RecordingsDatabase;
import com.example.sawt_al_amal.activity.soundRec.vr.record.list.RecordingsListActivity;
import com.example.sawt_al_amal.activity.soundRec.vr.record.recorder.ContinuousRecordingService;
import com.example.sawt_al_amal.activity.soundRec.vr.record.recorder.RecordActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.os.Vibrator;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQ_CODE_EDIT = 1;
    private static boolean isRecording = false;

    private static MainActivity2 contextRecognitionHandler;
    private Intent recorder;
    private Button startRecognitionButton;

    ProgressDialog progress;

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
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ResultsManager.setVibrator(v);

        final Button recordingsListButton = (Button) findViewById(R.id.recordings_list);
        final FloatingActionButton rightRecordButton = (FloatingActionButton) findViewById(R.id.fab);


        contextRecognitionHandler = this;
        startRecognitionButton = (Button) findViewById(R.id.start_recognition);
        startRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("11111111111111111111111 isRecording === " + isRecording);

                if (!isRecording) {
                    System.out.println("2222222 isRecording faaalse !!!!!!! ");
                    startRecognitionButton.setText("Stop recognition");
                    System.out.println("AAAA");
                    isRecording = true;
                    System.out.println("BBBB");
                    progress = ProgressDialog.show(MainActivity2.this, "Starting sound recognition",
                            "Please wait while loding sound patterns from your recordings list...",true);
                    System.out.println("CCCCC");
                    recordingsListButton.setVisibility(View.INVISIBLE);
                    System.out.println("DDDDD");
                    //rightRecordButton.setVisibility(View.INVISIBLE);
                    System.out.println("EEEEEE &&& progress === "+progress.toString());
                    ResultsManager.setProgressBar(progress);
                    System.out.println("FFFFFF");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            recorder = new Intent(contextRecognitionHandler, ContinuousRecordingService.class);
                            System.out.println("GGGGG &&& recorder === "+recorder.toString());
                            startService(recorder);
                            System.out.println("HHHHHHHHHHHHHHHHH  STARTED SERVICE &&& recorder === "+recorder.toString());
                        }
                    }).start();

                } else {
                    System.out.println("33333333333 isRecording true ////////");
                    startRecognitionButton.setText("Start recognition");
                    isRecording = false;
                    System.out.println("aaaaaaa");
                    stopService(recorder);
                    System.out.println("bbbbbbbb");
                    recordingsListButton.setVisibility(View.VISIBLE);
                    System.out.println("ccccccc");
                    rightRecordButton.setVisibility(View.VISIBLE);
                    System.out.println("ddddddddd");
                }
            }
        });
        System.out.println("zzzzZZZZZzzzzzzz");
        setupRecordingsListVisibility();
    }

    private void setupRecordingsListVisibility() {
        final View recordingsList = findViewById(R.id.recordings_list);

        final RecordingsDatabase recordingsDatabase = new RecordingsDatabase(this);
        if (recordingsDatabase.getAllRecordings().size() > 0) {
            recordingsList.setVisibility(View.VISIBLE);
            recordingsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(MainActivity2.this, RecordingsListActivity.class), REQ_CODE_EDIT);
                }
            });
        } else {
            recordingsList.setVisibility(View.GONE);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_licenses) {
            startActivity(new Intent(this, Licenses.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME || (keyCode == KeyEvent.KEYCODE_BACK)) {
            stop();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void stop() {
        if (isRecording) {
            startRecognitionButton.setText("Start recognition");
            isRecording = false;
            stopService(recorder);
        }
    }
}
