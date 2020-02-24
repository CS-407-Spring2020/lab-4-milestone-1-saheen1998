package com.example.lab4milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button butStartThread;
    private volatile boolean stopThread = false;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view) {
        stopThread = false;
        ExRunnable runnable = new ExRunnable(10);
        new Thread(runnable).start();
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExRunnable implements Runnable {

        int sec;

        ExRunnable(int sec) {
            this.sec = sec;
        }

        @Override
        public void run () {
            for(int i = 0; i < sec; i++) {
                if(stopThread) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            butStartThread.setText("Start");
                        }
                    });
                    return;
                }
                if(i == 5) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            butStartThread.setText("50%");
                        }
                    });
                }
                Log.d(TAG, "startThread: " + i);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    butStartThread.setText("Start");
                }
            });
        }
    }
}
