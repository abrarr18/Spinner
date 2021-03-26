package com.example.spinner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int count =0;
    Button playB;
    Spinner mainSong, sound1, sound2, sound3;
    SeekBar seekBar1, seekBar2, seekBar3;
    ArrayList<String> mainSongs, soundEffects;
    ArrayAdapter<String> main, s1, s2, s3;
    boolean isInitialized = false;
    public static final String INITIALIZE_STATUS = "true";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainSong = (Spinner) findViewById(R.id.spinnerMusic);
        mainSongs = new ArrayList<String>();
        mainSongs.add("Go Tech Go!");
        mainSongs.add("Tech Triumph");
        mainSongs.add("Magic In The Air");
        main = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mainSongs);
        main.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSong.setAdapter(main);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewMain) ;
        imageView.setImageResource(R.drawable.gohokies);
        sound1 = (Spinner) findViewById(R.id.spinnerSound1);
        sound2 = (Spinner) findViewById(R.id.spinnerSound2);
        sound3 = (Spinner) findViewById(R.id.spinnerSound3);
        soundEffects = new ArrayList<String>();
        soundEffects.add("Cheering");
        soundEffects.add("Clapping");
        soundEffects.add("Let's Go Hokies!");
        s1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, soundEffects);
        s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sound1.setAdapter(s1);
        s2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, soundEffects);
        s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sound2.setAdapter(s2);
        s3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, soundEffects);
        s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sound3.setAdapter(s3);
        playB= (Button) findViewById(R.id.playButton);
        playB.setOnClickListener(this);
        seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
        seekBar1.setMax(100);
        seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
        seekBar2.setMax(100);
        seekBar3 = (SeekBar) findViewById(R.id.seekbar3);
        seekBar3.setMax(100);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("main", mainSong.getSelectedItem().toString());
        intent.putExtra("effect1", sound1.getSelectedItem().toString());
        intent.putExtra("effect2", sound2.getSelectedItem().toString());
        intent.putExtra("effect3", sound3.getSelectedItem().toString());
        intent.putExtra("count", count);
        intent.putExtra("progress1", seekBar1.getProgress());
        intent.putExtra("progress2", seekBar2.getProgress());
        intent.putExtra("progress3", seekBar3.getProgress());
        count++;
        startActivity(intent);

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        super.onSaveInstanceState(outState);
    }
}