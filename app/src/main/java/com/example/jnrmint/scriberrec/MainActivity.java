package com.example.jnrmint.scriberrec;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private Button stopButton;
    private Button startButton;
    private Chronometer timer;
    private TextView text;
    private boolean mstarted = false;
    public TextView timerTextView;
    private long startHTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        text = findViewById(R.id.recmsg);
        startButton = findViewById(R.id.btnStart);
        stopButton = findViewById(R.id.btnStop);
        timerTextView = findViewById(R.id.rectimer);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!mstarted){
                    text.setText("Recording has started");
                    timer.start();
                    startHTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    mstarted = true;
                } else {
                    text.setText("Recording has started already!");
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mstarted){
                    text.setText("Recording has ended");
                    timer.stop();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    mstarted = false;
                } else {
                    text.setText("Recording has stopped already!");
                }

            }
        });


    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startHTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            if (timerTextView != null)
                timerTextView.setText("" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };




}
