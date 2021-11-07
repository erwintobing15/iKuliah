package com.chupakubera.ipkuliah.activity.catatan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chupakubera.ipkuliah.activity.jadwal.JadwalActivity;
import com.chupakubera.ipkuliah.activity.matkul.DaftarMatkulActivity;
import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.ipkuliah.database.DatabaseHelper;
import com.chupakubera.sqliteipkul.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatatanActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] note_from = new String[] { DatabaseHelper._ID, DatabaseHelper.DATESTAMP,
            DatabaseHelper.KETERANGAN, DatabaseHelper.NOTE};

    final int[] note_to = new int[] {R.id.idCatatan, R.id.datestamp, R.id.keterangan, R.id.note};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_catatan);


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.allCatatan();

        // floating button for insert jadwal
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_catatan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tambah_jadwal = new Intent(getApplicationContext(), TambahCatatanActivity.class);
                startActivity(tambah_jadwal);

            }
        });

        listView = (ListView) findViewById(R.id.list_view_catatan);
        listView.setEmptyView(findViewById(R.id.empty_catatan));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_catatan, cursor, note_from, note_to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);


        // OnCLickListener listview jadwal
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTextView = (TextView) view.findViewById(R.id.idCatatan);
                TextView datestampTextView = (TextView) view.findViewById(R.id.datestamp);
                TextView keteranganTextView = (TextView) view.findViewById(R.id.keterangan);
                TextView noteTextView = (TextView) view.findViewById(R.id.note);

                String _id = idTextView.getText().toString();
                String datestamp = datestampTextView.getText().toString();
                String keterangan = keteranganTextView.getText().toString();
                String note = noteTextView.getText().toString();


                Intent update_intent = new Intent(getApplicationContext(), UpdateCatatanActivity.class);
                update_intent.putExtra("id", _id);
                update_intent.putExtra("datestamp", datestamp);
                update_intent.putExtra("keterangan", keterangan);
                update_intent.putExtra("note", note);

                startActivity(update_intent);
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_catatan).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(CatatanActivity.this, DaftarMatkulActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(a);
                        break;
                    case R.id.navigation_jadwal:
                        Intent b = new Intent(CatatanActivity.this, JadwalActivity.class);
                        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(b);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
