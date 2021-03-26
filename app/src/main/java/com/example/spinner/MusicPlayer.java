package com.example.spinner;
import android.media.MediaPlayer;
public class MusicPlayer implements MediaPlayer.OnCompletionListener {
    boolean isPlaying = false;
    MediaPlayer musicPlayer, soundPlay1, soundPlay2, soundPlay3;
    int count=-1;
    int currentPosition, sound1, sound2, sound3 = 0;
    int a, b, c;
    int musicIndex = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;
    public static final int[] MUSICPATH = new int[]{
            R.raw.gotechgo,
            R.raw.techtriumph,
            R.raw.magicintheairnew
    };
    public static final int[] SOUNDNAME = new int[]{
            R.raw.cheering,
            R.raw.clapping,
            R.raw.lestgohokies
    };
    public static final String[] MUSICNAME = new String[]{
            "Go Tech Go!",
            "Tech Triumph",
            "Magic In The Air"
    };
    public MusicPlayer(MusicService service) {
        this.musicService = service;
    }
    public int getMusicStatus() {
        return musicStatus;
    }
    public String getMusicName() {
        return MUSICNAME[musicIndex];
    }
    public int getIndex(String str){
        for( int i = 0; i<MUSICNAME.length; i++){
            if(str.compareTo( MUSICNAME[i]) == 0){
                return i;
            }
        }
        return -1;
    }
    public void playMusic(int musicIdx, int soundP1, int soundP2, int soundP3, int progress1, int progress2, int progress3) {
        isPlaying = true;
        musicService.onUpdateMusicName(getMusicName());
        musicIndex= musicIdx;
        musicPlayer= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        soundPlay1 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP1]);
        soundPlay2 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP2]);
        soundPlay3 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP3]);
        musicPlayer.start();
        musicPlayer.setOnCompletionListener(this);
        soundPlay1.setOnCompletionListener(this);
        musicStatus = 1;
    }
    public int playEffect(int progress, int duration){
        return (progress * duration)/100;
    }

    public void pauseMusic() {
        if(musicPlayer!= null && musicPlayer.isPlaying()){
            musicPlayer.pause();
            currentPosition= musicPlayer.getCurrentPosition();
            musicStatus= 2;
        }
        if(soundPlay1!= null && soundPlay1.isPlaying()){
            soundPlay1.pause();
            sound1= soundPlay1.getCurrentPosition();
            musicStatus= 2;
        }
        if(soundPlay2!= null && soundPlay2.isPlaying()){
            soundPlay2.pause();
            sound2= soundPlay2.getCurrentPosition();
            musicStatus= 2;
        }
        if(soundPlay3!= null && soundPlay3.isPlaying()){
            soundPlay3.pause();
            sound3 = soundPlay3.getCurrentPosition();
            musicStatus= 2;
        }
    }

    public void resumeMusic(int musicIdx, int number , int soundP1, int soundP2, int soundP3, int progress1, int progress2, int progress3) {
        if(musicPlayer!= null){
            if(count == number){
                musicPlayer.seekTo(currentPosition);
                musicPlayer.start();
            }
            else{
                musicService.onUpdateMusicName(getMusicName());
                count = number;
                musicIndex= musicIdx;
                musicPlayer= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
                soundPlay1 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP1]);
                soundPlay2 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP2]);
                soundPlay3 = MediaPlayer.create(this.musicService, SOUNDNAME[soundP3]);
                musicPlayer.start();
                progress1 = playEffect(progress1, musicPlayer.getDuration());
                progress2 = playEffect(progress2, musicPlayer.getDuration());
                progress3 = playEffect(progress3, musicPlayer.getDuration());
                a = progress1;
                b = progress2;
                c = progress3;
            }
            musicStatus=1;
        }
    }
    public void restartMusic() {
        if(musicPlayer!= null && musicPlayer.isPlaying()){
            musicPlayer.stop();
            currentPosition= 0;
            musicStatus= 0;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }
}