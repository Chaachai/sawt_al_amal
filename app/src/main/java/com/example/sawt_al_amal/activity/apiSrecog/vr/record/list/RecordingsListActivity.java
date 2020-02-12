package com.example.sawt_al_amal.activity.apiSrecog.vr.record.list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sawt_al_amal.R;

//FEKRANE Zakaria
// cette classe permet de afficher les données recuperé depuis la base de donnée sous forme de des items dans la listview
public class RecordingsListActivity extends AppCompatActivity implements RecordingsAdapter.OnRemovedAllListener {


    private static final int REQ_CODE_EDIT = 1;

    private RecordingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//afficher l'action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recordingsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecordingsAdapter(this, this, REQ_CODE_EDIT);
        recyclerView.setAdapter(adapter);
    }
//afficher les audio dans l'interface
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_EDIT && resultCode == RESULT_OK) {
            // refresh list
            adapter.loadRecordings();
            adapter.notifyDataSetChanged();
        }
    }
///supprimer tout les audio
    @Override
    public void onRemovedAll() {
        Toast.makeText(this, R.string.removed_all_recordings, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
