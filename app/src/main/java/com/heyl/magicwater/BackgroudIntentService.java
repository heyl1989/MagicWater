package com.heyl.magicwater;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.heyl.magicwater.base.BaseActivity;
import com.heyl.magicwater.comman.Content;

import java.io.IOException;

public class BackgroudIntentService extends Service {

    private MediaPlayer mediaplayer;

    @Override
    public void onCreate() {
        mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.bgmusic);
        mediaplayer.setLooping(true);
    }

    /**
     * 广播接受者
     */
    BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            //播放
            if (action.equals(Content.START_VOL)) {
                mediaplayer.start();
            }
            //停止播放
            if (action.equals(Content.STOP_VOL)) {
                if (mediaplayer != null) {
                    if (mediaplayer.isPlaying())
                        mediaplayer.stop();
                    mediaplayer.reset();
                    mediaplayer.release();
                    mediaplayer = null;
                    mediaplayer = MediaPlayer.create(BackgroudIntentService.this, R.raw.bgmusic);
                    mediaplayer.setLooping(true);
                }
            }
        }
    };

    /**
     * 广播接受过滤器
     *
     * @return
     */
    private IntentFilter getFilter() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Content.START_VOL);
        mIntentFilter.addAction(Content.STOP_VOL);
        return mIntentFilter;
    }

    @Override
    public IBinder onBind(Intent intent) {
        LocalBroadcastManager.getInstance(this).registerReceiver(reciver, getFilter());
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public boolean onUnbind(Intent intent) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reciver);
        if (mediaplayer != null) {
            if (mediaplayer.isPlaying())
                mediaplayer.stop();
            mediaplayer.reset();
            mediaplayer.release();
            mediaplayer = null;
        }
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        BackgroudIntentService getService() {
            return BackgroudIntentService.this;
        }
    }

}
