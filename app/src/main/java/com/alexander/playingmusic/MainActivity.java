package com.alexander.playingmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    MaterialButton playButton;
    Button seekPreviousButton;
    Button seekNextButton;
    SeekBar seekBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.stuff);

        seekBar = findViewById(R.id.volumeSeekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
               if(b){
                   mediaPlayer.seekTo(progress);
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        playButton = (MaterialButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    pause();
                    playButton.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow));
                }else{
                    play();
                    playButton.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause));
                }
            }
        });

        seekPreviousButton = findViewById(R.id.previousSeekButton);
        seekPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekPrevious();
            }
        });

        seekNextButton = findViewById(R.id.nextSeekButton);
        seekNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekNext();
            }
        });

    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void seekPrevious() {
        seekBar.setProgress(0);
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        playButton.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow));
    }

    public void seekNext() {
        seekBar.setProgress(mediaPlayer.getDuration());
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        playButton.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow));
    }
}