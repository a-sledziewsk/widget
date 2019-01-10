package com.example.adam.mini_projekt_5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private static String PLAY = "PLAY";
    private static String STOP = "STOP";
    private static String NEXT = "NEXT";
    private static int[] trackList = {R.raw.sax_guy, R.raw.deja_vu};
    private static int indexList;
    private MediaPlayer mPlayer;
    private int mStartID;



    @Override
    public void onCreate(){
        super.onCreate();
        indexList = 0;
        mPlayer = MediaPlayer.create(this, trackList[indexList]);

        if (null != mPlayer) {

            mPlayer.setLooping(false);

            // Stop Service when music has finished playing
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    // stop Service if it was started with this ID
                    // Otherwise let other start commands proceed
                    stopSelf(mStartID);

                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        String action = intent.getAction();

//        if (null != mPlayer) {
//
//            // ID for this start command
//            mStartID = startid;
//
//            if (mPlayer.isPlaying()) {
//
//                // Rewind to beginning of song
//                mPlayer.seekTo(0);
//
//            } else {
//
//                // Start playing song
//                mPlayer.start();
//
//            }
//
//        }
        if (null != mPlayer) {
            if (action.equals(PLAY)) {
                mStartID = startid;

                if (mPlayer.isPlaying()) {

                    // Rewind to beginning of song
                    mPlayer.seekTo(0);

                } else {

                    // Start playing song
                    mPlayer.start();

                }
            } else if (action.equals(STOP)) {
                mPlayer.pause();
            }
            else if(action.equals(NEXT)){
                if (indexList == 1){
                    indexList = 0;
                }else{
                    indexList++;
                }
                mPlayer.stop();
                mPlayer = MediaPlayer.create(this, trackList[indexList]);
                mPlayer.start();
            }
        }
        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        if (null != mPlayer) {

            mPlayer.stop();
            mPlayer.release();

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
