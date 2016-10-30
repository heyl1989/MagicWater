package com.heyl.magicwater.view;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.heyl.magicwater.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 图片动态切换器
 *
 * */
public class AnimImageView {
    private static final int MSG_START = 0xf1;
    private static final int MSG_STOP  = 0xf2;
    private static final int STATE_STOP = 0xf3;
    private static final int STATE_RUNNING = 0xf4;

    /* 运行状态*/
    private int mState = STATE_RUNNING;
    private ImageView mImageView;
    /* 图片资源ID列表*/
    private List<Integer> mResourceIdList = new ArrayList<>();
    /* 定时任务*/
    private Timer mTimer = null;
    private AnimTimerTask mTimeTask = null;
    /* 记录播放位置*/
    private int mFrameIndex = 0;
    /* 播放形式*/
    private boolean isLooping = false;

    public AnimImageView( ){
        mTimer = new Timer();
    }

    /**
     * 设置动画播放资源
     *
     * */
    public void setAnimation(ImageView imageview){
        mImageView = imageview;
        mResourceIdList.add(R.drawable.water1);
        mResourceIdList.add(R.drawable.water2);
        mResourceIdList.add(R.drawable.water3);
        mResourceIdList.add(R.drawable.water4);
        mResourceIdList.add(R.drawable.water5);
        mResourceIdList.add(R.drawable.water6);
        mResourceIdList.add(R.drawable.water7);
        mResourceIdList.add(R.drawable.water8);
        mResourceIdList.add(R.drawable.water9);
        mResourceIdList.add(R.drawable.water10);
        mResourceIdList.add(R.drawable.water11);
        mResourceIdList.add(R.drawable.water12);
        mResourceIdList.add(R.drawable.water13);
        mResourceIdList.add(R.drawable.water14);
        mResourceIdList.add(R.drawable.water15);
        mResourceIdList.add(R.drawable.water16);
        mResourceIdList.add(R.drawable.water17);
        mResourceIdList.add(R.drawable.water18);
        mResourceIdList.add(R.drawable.water19);
        mResourceIdList.add(R.drawable.water20);
    }

    /**
     *  开始播放动画
     *  @param loop 是否循环播放
     *  @param duration 动画播放时间间隔
     * */
    public void start(boolean loop, int duration){
        //stop();
        isLooping = loop;
        mFrameIndex = 0;
        mState = STATE_RUNNING;
        mTimeTask = new AnimTimerTask( );
        mTimer.schedule(mTimeTask, 0, duration);
    }

    /**
     * 停止动画播放
     *
     * */
    public void stop(){
        if (mTimeTask != null) {
            mFrameIndex = 0;
            mState = STATE_STOP;
            mTimer.purge();
            mTimeTask.cancel();
            mTimeTask = null;
            mImageView.setBackgroundResource(0);
        }
    }

    /**
     * 定时器任务
     *
     *
     */
    class AnimTimerTask extends TimerTask {
        @Override
        public void run() {
            if(mFrameIndex < 0 || mState == STATE_STOP){
                return;
            }

            if( mFrameIndex < mResourceIdList.size() ){
                Message msg = AnimHanlder.obtainMessage(MSG_START,0,0,null);
                msg.sendToTarget();
            }else{
                mFrameIndex = 0;
                if(!isLooping){
                    Message msg = AnimHanlder.obtainMessage(MSG_STOP,0,0,null);
                    msg.sendToTarget();
                }
            }
        }
    }

    private Handler AnimHanlder = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_START:{
                    if(mFrameIndex >=0 && mFrameIndex < mResourceIdList.size() && mState == STATE_RUNNING){
                        mImageView.setImageResource(mResourceIdList.get(mFrameIndex));
                        mFrameIndex++;
                    }
                }
                break;
                case MSG_STOP:{
                    if (mTimeTask != null) {
                        mFrameIndex = 0;
                        mTimer.purge();
                        mTimeTask.cancel();
                        mState = STATE_STOP;
                        mTimeTask = null;
                        mImageView.setImageResource(0);
                    }
                }
                break;
                default:
                    break;
            }
        }
    };
}
