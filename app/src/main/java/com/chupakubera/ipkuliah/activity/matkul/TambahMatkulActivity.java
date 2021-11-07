package com.chupakubera.ipkuliah.activity.matkul;

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

public class TambahMatkulActivity extends Activity implements View.OnClickListener {
    private Button addTodoBtn;
    private EditText matkulEditText;
    private EditText sksEditText;
    private Spinner indeksSpinner;
    private Spinner semesterSpinner;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tambah Matakuliah");
        setContentView(R.layout.activity_tambah_matkul);

        matkulEditText = (EditText) findViewById(R.id.matkul_edittext);
        sksEditText = (EditText) findViewById(R.id.sks_edittext);
        indeksSpinner = (Spinner) findViewById(R.id.indeks_spinner);
        semesterSpinner = (Spinner) findViewById(R.id.semester_spinner);

        // add view to spinner
        indeksSpinner = (Spinner) findViewById(R.id.indeks_spinner);
        semesterSpinner = (Spinner) findViewById(R.id.semester_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter1 = ArrayAdapter
                .createFromResource(this, R.array.indeks_array,
                        android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                .createFromResource(this, R.array.semester_array,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter1
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticAdapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        indeksSpinner.setAdapter(staticAdapter1);
        semesterSpinner.setAdapter(staticAdapter2);


        addTodoBtn = (Button) findViewById(R.id.add_matkul);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_matkul:

                final String matkul = matkulEditText.getText().toString();
                String sks = sksEditText.getText().toString();
                // Get value of the spinner to variable indeks and semester
                final String indeks = indeksSpinner.getSelectedItem().toString();
                final String semester = semesterSpinner.getSelectedItem().toString();

                if (matkul.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan mata kuliah", Toast.LENGTH_SHORT).show();
                } else if (sks.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan sks", Toast.LENGTH_SHORT).show();
                } else if (indeks.equals("Nilai")) {
                    Toast.makeText(getBaseContext(), "Pilih nilai", Toast.LENGTH_SHORT).show();
                } else if (semester.equals("Semester")) {
                    Toast.makeText(getBaseContext(), "Pilih Semester", Toast.LENGTH_SHORT).show();
                } else {
                    int _sks = Integer.parseInt(sks);

                    dbManager.insertMatkul(matkul, _sks, indeks, semester);

                    Intent main = new Intent(TambahMatkulActivity.this, DaftarMatkulActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                }

                break;
        }
    }


}
