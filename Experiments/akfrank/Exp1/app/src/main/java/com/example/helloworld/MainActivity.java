package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Button playButton;
    Button stopButton;
    MediaPlayer mediaPlayer;
    boolean player_ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wintersunrise);
                    player_ready = true;
                    mediaPlayer.start();
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Toast.makeText(getApplicationContext(), "An error occurred...", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            player_ready = true;
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    });
                }
                else if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else if(player_ready){
                    mediaPlayer.start();
                }
                // wait for player to be ready
            }
        });

        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    player_ready = false;
                    mediaPlayer.prepareAsync();
                }
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        mediaPlayer.release();
        mediaPlayer = null;
        player_ready = false;
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wintersunrise);
            player_ready = true;
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(getApplicationContext(), "An error occurred...", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player_ready = true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null) mediaPlayer.release();
    }
}