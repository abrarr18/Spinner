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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView music;
    Button playButton, restartButton;
    ImageView image;
    int count = -1;
    int musicIdx, soundIdx1, soundIdx2, soundIdx3, sbprog1, sbprog2, sbprog3;
    String mainMusic;
    MusicService musicService;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    boolean isPlaying = false;
    boolean isInitialized = false;
    public static final String INITIALIZE_STATUS = "true";
    public static final String MUSIC_PLAYING = "true";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        image =(ImageView) findViewById(R.id.imageOfMusicPlaying);
        image.setImageResource(R.drawable.techtriumphvpi);
        music= (TextView) findViewById(R.id.musicName);
        playButton= (Button) findViewById(R.id.buttonPlaym);
        restartButton = (Button) findViewById(R.id.buttonRestartm);
        playButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        Bundle b1 = getIntent().getExtras();
        mainMusic = b1.getString("main");
        music.setText(mainMusic);
        int temp = count;
        count = b1.getInt("count");
        if(temp != count){
            playButton.setText("Play");
        }
        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            music.setText(savedInstanceState.getString(MUSIC_PLAYING));
        }
        startMusicServiceIntent= new Intent(this, MusicService.class);
        if(!isInitialized){
            startService(startMusicServiceIntent);
            isInitialized= true;
        }
        musicCompletionReceiver = new MusicCompletionReceiver(this);
    }
    @Override
    public void onClick(View view) {
        if (isPlaying) {
            switch (view.getId()) {
                case R.id.buttonPlaym:
                    switch (musicService.getPlayingStatus()) {
                        case 0:
                            musicIdx = musicService.getMusicIndex(mainMusic);
                            musicService.startMusic(musicIdx, soundIdx1, soundIdx2, soundIdx3, sbprog1, sbprog2, sbprog3);
                            playButton.setText("Pause");
                            break;
                        case 1:
                            musicService.pauseMusic();
                            playButton.setText("Resume");
                            break;
                        case 2:
                            musicIdx = musicService.getMusicIndex(mainMusic);
                            musicService.resumeMusic(musicIdx, count,  soundIdx1, soundIdx2, soundIdx3, sbprog1, sbprog2, sbprog3);
                            playButton.setText("Pause");
                            break;
                    }
                    break;
                case R.id.buttonRestartm:
                    musicService.restartMusic();
                    playButton.setText("Play");
                    break;
            }

        }
    }
    public void updatePicture(String musicName) {
        music.setText(musicName);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isInitialized && !isPlaying){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }
        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETEINTENT));
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(isPlaying){
            unbindService(musicServiceConnection);
            isPlaying = false;
        }
        unregisterReceiver(musicCompletionReceiver);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putString(MUSIC_PLAYING, music.getText().toString());
        super.onSaveInstanceState(outState);
    }
    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isPlaying = true;
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            musicService = binder.getService();
            switch (musicService.getPlayingStatus()) {
                case 0:
                    playButton.setText("Play");
                    break;
                case 1:
                    playButton.setText("Pause");
                    break;
                case 2:
                    playButton.setText("Resume");
                    break;
            }
        }
        public void updatePicture(String musicName) {
            switch(musicName){
                case "Go Tech Go!":
                    image.setImageResource(R.drawable.gotechgo);
                    break;
                case "Magic In The Air":
                    image.setImageResource(R.drawable.magicintheair);
                    break;
                case "Tech Triumph":
                    image.setImageResource(R.drawable.techtriumphvpi);
                    break;
                case "Cheering":
                    image.setImageResource(R.drawable.cheeringvt);
                    break;
                case "Clapping":
                    image.setImageResource(R.drawable.clapping);
                    break;
                case"Let's Go Hokies!":
                    image.setImageResource(R.drawable.letsgohokies);
                    break;
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isPlaying = false;
            musicService = null;
        }
    };
}