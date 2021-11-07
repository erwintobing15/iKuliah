package com.chupakubera.ipkuliah.activity.jadwal;

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

import com.chupakubera.ipkuliah.activity.catatan.CatatanActivity;
import com.chupakubera.ipkuliah.activity.matkul.DaftarMatkulActivity;
import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.ipkuliah.database.DatabaseHelper;
import com.chupakubera.sqliteipkul.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JadwalActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] jadwal_from = new String[] { DatabaseHelper._ID, DatabaseHelper.MATKUL_JADWAL,
            DatabaseHelper.HARI, DatabaseHelper.WAKTU, DatabaseHelper.RUANGAN};

    final int[] jadwal_to = new int[] {R.id.idJadwal, R.id.matkulJadwal, R.id.hari, R.id.waktu, R.id.ruangan};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_jadwal);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.allJadwal();

        TextView textViewMatkul = (TextView) findViewById(R.id.matkul_jadwal);
        TextView textViewHari = (TextView) findViewById(R.id.hari_jadwal);
        TextView textViewWaktu = (TextView) findViewById(R.id.waktu_jadwal);
        TextView textViewRuangan = (TextView) findViewById(R.id.ruangan_jadwal);

        int jadwal = dbManager.getCountJadwal();

        if (jadwal == 0) {
            textViewMatkul.setVisibility(View.GONE);
            textViewHari.setVisibility(View.GONE);
            textViewWaktu.setVisibility(View.GONE);
            textViewRuangan.setVisibility(View.GONE);
        }
        else {
            textViewMatkul.setVisibility(View.VISIBLE);
            textViewHari.setVisibility(View.VISIBLE);
            textViewWaktu.setVisibility(View.VISIBLE);
            textViewRuangan.setVisibility(View.VISIBLE);
        }

        listView = (ListView) findViewById(R.id.list_view_jadwal);
        listView.setEmptyView(findViewById(R.id.empty_jadwal));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_jadwal, cursor, jadwal_from, jadwal_to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener listview jadwal
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTextView = (TextView) view.findViewById(R.id.idJadwal);
                TextView matkulTextView = (TextView) view.findViewById(R.id.matkulJadwal);
                TextView hariTextView = (TextView) view.findViewById(R.id.hari);
                TextView waktuTextView = (TextView) view.findViewById(R.id.waktu);
                TextView ruanganTextView = (TextView) view.findViewById(R.id.ruangan);

                String _id = idTextView.getText().toString();
                String matkul = matkulTextView.getText().toString();
                String hari = hariTextView.getText().toString();
                String waktu = waktuTextView.getText().toString();
                String ruangan = ruanganTextView.getText().toString();


                Intent update_intent = new Intent(getApplicationContext(), UpdateJadwalActivity.class);
                update_intent.putExtra("id", _id);
                update_intent.putExtra("matkul", matkul);
                update_intent.putExtra("hari", hari);
                update_intent.putExtra("waktu", waktu);
                update_intent.putExtra("ruangan", ruangan);

                startActivity(update_intent);
            }
        });


        // floating button for insert jadwal
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_jadwal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tambah_jadwal = new Intent(getApplicationContext(), TambahJadwalActivity.class);
                startActivity(tambah_jadwal);

            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_jadwal).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(JadwalActivity.this, DaftarMatkulActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(a);
                        break;
                    case R.id.navigation_jadwal:
                        break;
                    case R.id.navigation_catatan:
                        Intent b = new Intent(JadwalActivity.this, CatatanActivity.class);
                        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }
}
