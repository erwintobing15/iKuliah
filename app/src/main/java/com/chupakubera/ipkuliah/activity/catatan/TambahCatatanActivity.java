package com.chupakubera.ipkuliah.activity.catatan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.sqliteipkul.R;

public class TambahCatatanActivity extends Activity implements View.OnClickListener {

    private Button addTodoBtn;
    private EditText keterangEditText;
    private EditText noteEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tambah Catatan");
        setContentView(R.layout.activity_tambah_catatan);

        keterangEditText = (EditText) findViewById(R.id.keterangan_edittext);
        noteEditText = (EditText) findViewById(R.id.note_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_catatan);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_catatan:

                final String keterangan = keterangEditText.getText().toString();
                final String note = noteEditText.getText().toString();

                if (keterangan.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan keterangan.", Toast.LENGTH_SHORT).show();
                } else if (note.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan catatan anda.", Toast.LENGTH_SHORT).show();
                }  else {
                    dbManager.insertCatatan(keterangan, note);

                    Intent main = new Intent(TambahCatatanActivity.this, CatatanActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                }

                break;
        }
    }
}
