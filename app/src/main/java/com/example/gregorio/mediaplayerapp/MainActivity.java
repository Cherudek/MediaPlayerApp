package com.example.gregorio.mediaplayerapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Private Variables For Play, Pause, Rewind and Forward Buttons
    private Button play, pause, rewind, forward;

    //Variable initializing the Media Player object
    private MediaPlayer mMediaPlayer;

    //Variables initializing the start time and the final time
    private double startTime = 0;
    private double finalTime = 0;

    //Nee to check this one
    private Handler myHandler = new Handler();

    //Variable initializing the amount of time in milliseconds to forward or rewind
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    //Seek Bar Variable
    private SeekBar seekbar;

    //Text Variables for Song name, current play time, total duration
    private TextView tx1,tx2,tx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        View play = (Button) getResources(R.raw.title_theme);
    }
}
