package com.chupakubera.ipkuliah.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chupakubera.ipkuliah.activity.matkul.DaftarMatkulActivity;
import com.chupakubera.sqliteipkul.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 3 detik waktu loading screen
        int loading_time = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // loading screen  3 detik lalu pindah ke home
                Intent home = new Intent(SplashActivity.this, DaftarMatkulActivity.class);
                startActivity(home);
                finish();

            }
        }, loading_time);
    }
}
