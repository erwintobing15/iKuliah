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


public class UpdateMatkulActivity extends Activity implements View.OnClickListener {

    private EditText matkulText;
    private EditText sksText;
    private Spinner indeksSpinner;
    private Spinner semesterSpinner;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_matkul);

        dbManager = new DBManager(this);
        dbManager.open();

        matkulText = (EditText) findViewById(R.id.matkul_edittext);
        sksText = (EditText) findViewById(R.id.sks_edittext);
        indeksSpinner = (Spinner) findViewById(R.id.indeks_spinner);
        semesterSpinner = (Spinner) findViewById(R.id.semester_spinner);


        Button updateBtn = (Button) findViewById(R.id.btn_update_matkul);
        Button deleteBtn = (Button) findViewById(R.id.btn_delete_matkul);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String matkul = intent.getStringExtra("matkul");
        String sks = intent.getStringExtra("sks");
        String indeks = intent.getStringExtra("indeks");
        String semester = intent.getStringExtra("semester");

        assert id != null;
        _id = Long.parseLong(id);

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

        matkulText.setText(matkul);
        sksText.setText(sks);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_matkul:
                String matkul = matkulText.getText().toString();
                String sks = sksText.getText().toString();
                // Get value of the spinner to variable indeks and semester
                String indeks = indeksSpinner.getSelectedItem().toString();
                String semester = semesterSpinner.getSelectedItem().toString();

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

                    dbManager.updateMatkul(_id, matkul, _sks, indeks, semester);

                    this.returnHome();
                }

                break;
            case R.id.btn_delete_matkul:
                dbManager.deleteMatkul(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), DaftarMatkulActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
