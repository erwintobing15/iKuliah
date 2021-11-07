package com.chupakubera.ipkuliah.activity.matkul;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chupakubera.ipkuliah.activity.catatan.CatatanActivity;
import com.chupakubera.ipkuliah.activity.jadwal.JadwalActivity;
import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.ipkuliah.database.DatabaseHelper;
import com.chupakubera.sqliteipkul.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DaftarMatkulActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID, DatabaseHelper.MATKUL,
            DatabaseHelper.SKS, DatabaseHelper.INDEKS, DatabaseHelper.SEMESTER, DatabaseHelper.BOBOT };

    final int[] to = new int[] {R.id.id, R.id.matkul, R.id.sks, R.id.indeks, R.id.semester, R.id.bobot};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daftar_matkul);


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.allMatkul();


        TextView textViewMatkul = (TextView) findViewById(R.id.judul_matkul);
        TextView textViewSks = (TextView) findViewById(R.id.judul_sks);
        TextView textViewNilai = (TextView) findViewById(R.id.judul_indeks);
        TextView textViewSemester = (TextView) findViewById(R.id.judul_semester);

        int sks = dbManager.getSumSks();

        if (sks == 0) {
            textViewMatkul.setVisibility(View.GONE);
            textViewSks.setVisibility(View.GONE);
            textViewNilai.setVisibility(View.GONE);
            textViewSemester.setVisibility(View.GONE);
        }
        else {
            textViewMatkul.setVisibility(View.VISIBLE);
            textViewSks.setVisibility(View.VISIBLE);
            textViewNilai.setVisibility(View.VISIBLE);
            textViewSemester.setVisibility(View.VISIBLE);
        }

        listView = (ListView) findViewById(R.id.list_view_matkul);
        listView.setEmptyView(findViewById(R.id.empty_matkul));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_matkul, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener listview daftar matakuliah
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView matkulTextView = (TextView) view.findViewById(R.id.matkul);
                TextView sksTextView = (TextView) view.findViewById(R.id.sks);
                TextView indeksTextView = (TextView) view.findViewById(R.id.indeks);
                TextView semesterTextView = (TextView) view.findViewById(R.id.semester);
                TextView jumlah_sksTextView = (TextView) view.findViewById(R.id.bobot);

                String _id = idTextView.getText().toString();
                String matkul = matkulTextView.getText().toString();
                String sks = sksTextView.getText().toString();
                String indeks = indeksTextView.getText().toString();
                String semester = semesterTextView.getText().toString();
                String jumlah_sks = jumlah_sksTextView.getText().toString();


                Intent update_intent = new Intent(getApplicationContext(), UpdateMatkulActivity.class);
                update_intent.putExtra("id", _id);
                update_intent.putExtra("matkul", matkul);
                update_intent.putExtra("sks", sks);
                update_intent.putExtra("indeks", indeks);
                update_intent.putExtra("semester", semester);
                update_intent.putExtra("jumlah_sks", jumlah_sks);

                startActivity(update_intent);
            }
        });

        // floating button for insert matkul
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_matkul);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tambah_matkul = new Intent(getApplicationContext(), TambahMatkulActivity.class);
                startActivity(tambah_matkul);

            }
        });

        BottomNavigationItemView home =(BottomNavigationItemView) findViewById(R.id.navigation_home);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_jadwal:
                        Intent a = new Intent(DaftarMatkulActivity.this, JadwalActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(a);
                        break;
                    case R.id.navigation_catatan:
                        Intent b = new Intent(DaftarMatkulActivity.this, CatatanActivity.class);
                        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.tampil_ipk) {

            Intent add_mem = new Intent(this, TampilkanIpkActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }

}