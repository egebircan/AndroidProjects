package com.example.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 100) {
            public void onTick(long msUntilDone) {
                Log.i("Kalan süre", String.valueOf(msUntilDone / 1000));
            }

            public void onFinish() {
                Log.i("STATUS", "DONE");
            }
        }.start();

        /*
        final Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.i("MSG", "3 saniye geçmişten geldim");
                handler.postDelayed(this, 3000);
            }
        };

        handler.post(run);
        */

    }
}