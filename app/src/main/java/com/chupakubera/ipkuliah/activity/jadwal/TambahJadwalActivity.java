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

public class TambahJadwalActivity extends Activity implements View.OnClickListener {
    private Button addTodoBtn;
    private EditText matkulEditText;
    private EditText waktuEditText;
    private EditText ruanganEditText;

    private Spinner staticSpinner;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tambah Jadwal");
        setContentView(R.layout.activity_tambah_jadwal);

        matkulEditText = (EditText) findViewById(R.id.matkulJadwal_edittext);
        waktuEditText = (EditText) findViewById(R.id.waktu_edittext);
        ruanganEditText = (EditText) findViewById(R.id.ruangan_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_jadwal);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);

        // add view to spinner
        staticSpinner = (Spinner) findViewById(R.id.hari_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.hari_array,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        // Get value of the spinner to variable hari
        String hari = staticSpinner.getSelectedItem().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_jadwal:

                final String matkul = matkulEditText.getText().toString();
                final String hari = staticSpinner.getSelectedItem().toString();
                final String waktu = waktuEditText.getText().toString();
                final String ruangan = ruanganEditText.getText().toString();

                if (matkul.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan mata kuliah", Toast.LENGTH_SHORT).show();
                } else if (hari.equals("Pilih Hari")) {
                    Toast.makeText(getBaseContext(), "Pilih salah satu hari", Toast.LENGTH_SHORT).show();
                } else if (waktu.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan waktu kuliah", Toast.LENGTH_SHORT).show();
                } else if (ruangan.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan ruangan", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.insertJadwal(matkul, hari, waktu, ruangan);

                    Intent main = new Intent(TambahJadwalActivity.this, JadwalActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                }

                break;
        }
    }
}
