package com.example.spinner;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
public class MusicService extends Service {
    MusicPlayer musicPlayer;
    private final IBinder iBinder= new MyBinder();
    public static final String COMPLETEINTENT = "true";
    public static final String MUSICNAME = "true";
    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
    }
    public void startMusic(int musicIdx, int soundP1, int soundP2, int soundP3, int progress1, int progress2, int progress3){
        musicPlayer.playMusic(musicIdx, soundP1, soundP2, soundP3, progress1, progress2, progress3);
    }
    public void pauseMusic(){
        musicPlayer.pauseMusic();
    }
    public void resumeMusic(int musicIdx, int number, int soundP1, int soundP2, int soundP3, int progress1, int progress2, int progress3){
        musicPlayer.resumeMusic(musicIdx, number, soundP1, soundP2, soundP3, progress1, progress2, progress3);
    }
    public void restartMusic(){
        musicPlayer.restartMusic();
    }
    public int getPlayingStatus(){
        return musicPlayer.getMusicStatus();
    }
    public int getMusicIndex(String str){
        return musicPlayer.getIndex(str);
    }
    public void onUpdateMusicName(String musicname) {
        Intent intent = new Intent(COMPLETEINTENT);
        intent.putExtra(MUSICNAME, musicname);
        sendBroadcast(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    public class MyBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }
}