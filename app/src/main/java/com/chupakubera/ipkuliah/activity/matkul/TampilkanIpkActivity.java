package com.chupakubera.ipkuliah.activity.matkul;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chupakubera.ipkuliah.database.DBManager;
import com.chupakubera.sqliteipkul.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class TampilkanIpkActivity extends Activity{

    private TextView total_sks;
    private TextView total_bobot;
    private TextView ipk;
    private int totalSks;
    private double totalBobot;
    private double hasil_ipk;
    private DBManager dbManager;
    private AdView mAdView;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Indeks Prestasimu");
        setContentView(R.layout.activity_tampilkan_ipk);

        // banner add load up
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // declare and open database
        dbManager = new DBManager(this);
        dbManager.open();
        totalSks = dbManager.getSumSks();
        totalBobot = dbManager.getSumBobot();
        if (totalSks == 0) {
            hasil_ipk = totalBobot / 1;
        } else {
            hasil_ipk = totalBobot / totalSks;
        }

        total_sks = (TextView) findViewById(R.id.total_sks);
        total_sks.setText(Integer.toString(totalSks));
        total_bobot = (TextView) findViewById(R.id.total_bobot);
        total_bobot.setText(Double.toString(totalBobot));
        ipk = (TextView) findViewById(R.id.ipk);
        ipk.setText(String.format("%.2f", hasil_ipk));

        // close database
        dbManager.close();

        Button closeButton = (Button) findViewById(R.id.exit);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO:
                // This function closes Activity TampilkanIpkActivity
                finish();
            }
        });
    }
}
