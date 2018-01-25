package com.example.jnrmint.scriberrec;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    //To RECORD AUDIO
    private Button stopButton;
    private Button startButton;

    private Timer time;

    private MediaRecorder sRecorder;

    private boolean mstarted = true;

    private String mFileName;

    //TO PLAY AUDIO

    private Button playButton;
    private Button stopPlaying;

    private ImageButton pbtn;
    private ImageButton sbtn;

    private Drawable ic_pause_btn = getDrawable();

    private MediaPlayer sPlayer;

    private boolean sPlaying = true;

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private TextView text;

    public TextView timerTextView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.recmsg);

        startButton = findViewById(R.id.btnStart);
        stopButton = findViewById(R.id.btnStop);

        sbtn = findViewById(R.id.stopic);
        pbtn = findViewById(R.id.playic);

        playButton = findViewById(R.id.btnPlay);
        stopPlaying = findViewById(R.id.btnPause);

        timerTextView = findViewById(R.id.rectimer);
        time = new Timer();
        time.setClock(timerTextView);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "audioTest.3gp";

        //text.setText(mFileName);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mstarted);
                if(mstarted){
                    text.setText("Recording has started");
                    time.startTimer();
                } else {
                    text.setText("Recording has started already!");
                }
                mstarted = !mstarted;
            }
        });

        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mstarted);
                if(mstarted){
                    text.setText("Recording has started");
                    time.startTimer();
                    pbtn.setImageDrawable(ic_pause_btn);
                } else {
                    text.setText("Recording has started already!");
                }
                mstarted = !mstarted;
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mstarted);
                if(!mstarted){
                    text.setText("Recording has ended");
                    time.stopTimer();
                } else {
                    text.setText("Recording has stopped already!");
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mstarted);
                if(!mstarted){
                    text.setText("Recording has ended");
                    time.stopTimer();
                } else {
                    text.setText("Recording has stopped already!");
                }
            }
        });
        //playing and pausing playing
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(sPlaying);
                if(sPlaying){
                    playButton.setText("Stop");
                    text.setText("Player has Started");
                } else {
                    playButton.setText("Play");
                    text.setText("Player has been stopped");
                }
                sPlaying = !sPlaying;
            }
        });
    }

    /**
     * Recording functionality using MediaRecorder
     */

    private void onRecord(boolean start) {
            if (start) {
                startRecording();
            } else {
                stopRecording();
            }

    }

    private void startRecording() {
        sRecorder = new MediaRecorder();
        sRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        sRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        sRecorder.setOutputFile(mFileName);
        sRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            sRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        sRecorder.start();
    }

    private void stopRecording() {
        sRecorder.stop();
        sRecorder.release();
        sRecorder = null;
    }

    /**
     * Playback functionality using MediaPlayer.
     */

    private void onPlay(boolean start){
        //if(sPlayerStart) {
            if(start) startPlayer();
            else stopPlayer();
        /*} else {
            if (start) sPlayer.pause();
            else stopPlayer(); sPlayerStart = false;
        }*/
    }

    private void startPlayer(){
        sPlayer.release();
        sPlayer = new MediaPlayer();

        try {
            sPlayer.setDataSource(mFileName);
            sPlayer.prepare();
            sPlayer.start();
        }   catch (IOException e){
            Log.e(LOG_TAG, "prepare() method has failed");
        }
    }

    private void stopPlayer(){
        sPlayer.release();
        sPlayer = null;
    }





}
