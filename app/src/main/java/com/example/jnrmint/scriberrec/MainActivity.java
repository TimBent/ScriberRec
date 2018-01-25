package com.example.jnrmint.scriberrec;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        text = findViewById(R.id.recmsg);
        startButton = findViewById(R.id.btnStart);
        stopButton = findViewById(R.id.btnStop);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!mstarted){
                    text.setText("Recording has started");
                    timer.start();
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
                    mstarted = false;
                } else {
                    text.setText("Recording has stopped already!");
                }

            }
        });
    }



}
