package com.heyl.magicwater.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.heyl.magicwater.R;
import com.heyl.magicwater.util.SizeUtil;

public abstract class BaseActivity extends Activity {

    protected SoundPool mSoundPool;
    private int musicClick;
    private int musicPour;
    private int musicGroundWater;
    private int streamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    @Override
    protected void onResume() {
        mSoundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        //第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        musicClick = mSoundPool.load(this, R.raw.shui_di, 1);
        musicPour = mSoundPool.load(this, R.raw.pour, 1);
        //musicGroundWater = mSoundPool.load(this, R.raw.ground_water, 1);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSoundPool != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSoundPool.release();
                    mSoundPool = null;
                }
            },600);
        }
    }

    protected abstract void initView();

    /**
     * 按键音
     */
    protected void clickMusic() {
        streamID = mSoundPool.play(musicClick, 1, 1, 0, -1, 1);
    }

    /**
     * 接水
     */
    protected void groundWater() {
        streamID = mSoundPool.play(musicPour, 1, 1, 0, -1, 1);
    }

    /**
     * 到水
     */
    protected void pourWater() {
        streamID = mSoundPool.play(musicPour, 1, 1, 0, -1, 1);
    }

    /**
     *
     */
    private void stopSound() {
        if (mSoundPool != null && streamID > 0) {
            mSoundPool.stop(streamID);
            mSoundPool.release();
        }

    }

    /**
     * 自定义吐司
     *
     * @param s
     */
    protected void showTost(String s) {
        Toast toast = new Toast(this);
        TextView text = new TextView(this);
        text.setBackgroundResource(R.drawable.round_angle);
        text.setPadding(10, 5, 10, 5);
        text.setTextSize(18);
        text.setTextColor(Color.WHITE);
        text.setText(s);
        toast.setView(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }

    protected void goOther(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void goOther(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
