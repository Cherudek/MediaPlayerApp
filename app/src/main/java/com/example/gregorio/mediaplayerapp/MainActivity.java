package com.example.gregorio.mediaplayerapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static int oneTimeOnly = 0;
    //Private Variables For Play, Pause, Rewind and Forward Buttons
    private Button play, pause, rewind, forward;
    //Variable initializing the Media Player object
    private MediaPlayer mediaPlayer;
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
    private TextView tx1, tx2, tx3;
    //?
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );

            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //assign to our button variables a corrispondent item in the activity_main xml
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        rewind = (Button) findViewById(R.id.rewind);
        forward = (Button) findViewById(R.id.forward);

        //start time text view assignment
        tx1 = (TextView) findViewById(R.id.start_time);

        //duration assigned to text view
        tx2 = (TextView) findViewById(R.id.duration);

        //Song title attached to textView
        tx3 = (TextView) findViewById(R.id.song_name);
        tx3.setText("Fever");


        //initializing the media player object with fever sound from the raw resources folder
        mediaPlayer = MediaPlayer.create(this, R.raw.fever);

        //initializing the seekbar variable to its xml id
        seekbar = (SeekBar) findViewById(R.id.seek_bar);

        //set the seekbar status to false.
        seekbar.setClickable(false);

        //set the pause button to disable.
        pause.setEnabled(false);

        //add an onclick listener function to the Pause button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast message when I click on the Play Button
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();

                //Start method to the media player object
                mediaPlayer.start();

                //get the duration and current position methods for the media player object
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                //
                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                //Duration of the song
                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                //Current time of the song
                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                //Seek bar progress time
                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });

        // Setting an onClick listener function to the Pause button
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });

        //OnClickListener function added for the forward button
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //OnClickListener function added for the rewind button
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
