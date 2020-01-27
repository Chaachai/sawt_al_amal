package com.example.sawt_al_amal.activity.soundRec.vr.record.list;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.example.sawt_al_amal.R;


public class RecordingsListActivity extends AppCompatActivity implements RecordingsAdapter.OnRemovedAllListener {

    private static final String TAG = RecordingsListActivity.class.getSimpleName();

    private static final int REQ_CODE_EDIT = 1;
    private RecordingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recordingsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecordingsAdapter(this, this, REQ_CODE_EDIT);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_EDIT && resultCode == RESULT_OK) {
            // refresh list
            adapter.loadRecordings();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemovedAll() {
        Toast.makeText(this, R.string.removed_all_recordings, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
