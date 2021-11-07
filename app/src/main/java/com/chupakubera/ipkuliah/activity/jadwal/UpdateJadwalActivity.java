package com.chupakubera.ipkuliah.activity.jadwal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.sqliteipkul.R;

public class UpdateJadwalActivity extends Activity implements View.OnClickListener {
    private EditText matkulText;
    private Spinner hariText;
    private EditText waktuText;
    private EditText ruanganText;

    private Button updateBtn, deleteBtn;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Update Jadwal");
        setContentView(R.layout.activity_update_jadwal);

        dbManager = new DBManager(this);
        dbManager.open();

        matkulText = (EditText) findViewById(R.id.matkulJadwal_edittext);
        hariText = (Spinner) findViewById(R.id.hari_spinner);
        waktuText = (EditText) findViewById(R.id.waktu_edittext);
        ruanganText = (EditText) findViewById(R.id.ruangan_edittext);


        updateBtn = (Button) findViewById(R.id.btn_update_jadwal);
        deleteBtn = (Button) findViewById(R.id.btn_delete_jadwal);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String matkul = intent.getStringExtra("matkul");
        String waktu = intent.getStringExtra("waktu");
        String ruangan = intent.getStringExtra("ruangan");

        _id = Long.parseLong(id);

        // add view to spinner
        hariText = (Spinner) findViewById(R.id.hari_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.hari_array,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        hariText.setAdapter(staticAdapter);


        matkulText.setText(matkul);
        waktuText.setText(waktu);
        ruanganText.setText(ruangan);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_jadwal:
                String matkul = matkulText.getText().toString();
                String waktu = waktuText.getText().toString();
                String ruangan = ruanganText.getText().toString();

                // Get value of the spinner to variable hari
                String hari = hariText.getSelectedItem().toString();

                if (matkul.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan mata kuliah", Toast.LENGTH_SHORT).show();
                } else if (hari.equals("Pilih Hari")) {
                    Toast.makeText(getBaseContext(), "Isikan hari", Toast.LENGTH_SHORT).show();
                } else if (waktu.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan waktu kuliah", Toast.LENGTH_SHORT).show();
                } else if (ruangan.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan ruangan", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.updateJadwal(_id, matkul, hari, waktu, ruangan);
                    this.returnJadwal();
                }

                break;

            case R.id.btn_delete_jadwal:
                dbManager.deleteJadwal(_id);
                this.returnJadwal();
                break;
        }
    }

    public void returnJadwal() {
        Intent jadwal_intent = new Intent(getApplicationContext(), JadwalActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(jadwal_intent);
    }
}
