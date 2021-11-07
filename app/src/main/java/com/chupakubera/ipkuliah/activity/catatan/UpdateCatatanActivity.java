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

public class UpdateCatatanActivity extends Activity implements View.OnClickListener {
    private EditText keteranganText;
    private EditText noteText;

    private Button updateBtn, deleteBtn;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Update Catatan");
        setContentView(R.layout.activity_update_catatan);

        dbManager = new DBManager(this);
        dbManager.open();

        keteranganText = (EditText) findViewById(R.id.keterangan_edittext);
        noteText = (EditText) findViewById(R.id.note_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update_catatan);
        deleteBtn = (Button) findViewById(R.id.btn_delete_catatan);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String keterangan = intent.getStringExtra("keterangan");
        String note = intent.getStringExtra("note");

        _id = Long.parseLong(id);

        keteranganText.setText(keterangan);
        noteText.setText(note);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_catatan:
                String keterangan = keteranganText.getText().toString();
                String note = noteText.getText().toString();

                if (keterangan.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan keterangan", Toast.LENGTH_SHORT).show();
                } else if (note.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan catatan anda", Toast.LENGTH_SHORT).show();
                }  else {
                    dbManager.updateCatatan(_id, keterangan, note);
                    this.returnCatatan();
                }

                break;

            case R.id.btn_delete_catatan:
                dbManager.deleteCatatan(_id);
                this.returnCatatan();
                break;
        }
    }



    public void returnCatatan(){
        Intent catatan_intent = new Intent(getApplicationContext(), CatatanActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(catatan_intent);
    }
}
