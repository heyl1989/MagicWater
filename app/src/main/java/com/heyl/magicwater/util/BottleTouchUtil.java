package com.heyl.magicwater.util;

import android.animation.Animator;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.heyl.magicwater.App.AppBus;
import com.heyl.magicwater.model.BusEventData;

/**
 * Created by heyl on 2016/11/11.
 */

public class BottleTouchUtil {

    private static int startX;
    private static int startY;

    /**
     * Touch事件
     * @param view
     */
    public static void changedLocation(final Activity context , final View view) {
        //触摸监听事件
        view.setOnTouchListener(new View.OnTouchListener() {
            //v ： 代表当前控件
            //event ： 触摸事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                Log.e("触摸事件", event.getAction() + "");
                //getAction() :获取触摸事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下
                        //1.获取开始x和y的坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        AppBus.getInstance().post(new BusEventData(v.getLeft(),v.getTop(),v.getLeft()+v.getWidth(),v.getTop()+v.getHeight(),
                                MotionEvent.ACTION_DOWN,view));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //移动
                        //2.移动到新的位置，记录新的位置的x和y的坐标
                        int newX = (int) event.getRawX();
                        int newY = (int) event.getRawY();
                        //3.计算偏移量
                        int dX = newX - startX;
                        int dY = newY - startY;
                        //4.移动相应的偏移量，重新分配位置
                        int l = v.getLeft();//获取控件距离左边框的距离     原控件
                        int t = v.getTop();//获取控件距离顶部的距离  原控件
                        //获取最新的距离
                        l += dX;
                        t += dY;
                        //重新绘制控件
                        //l:距离左边框的距离
                        //t:距离顶部的距离
                        //r:控件的右边框距离屏幕的左边框的距离
                        //b:控件的底部距离屏幕的顶部的距离
                        if (t <= 0) {
                            t = 0;
                        }
                        if (t >= SizeUtil.getScreenHeight(context) - v.getHeight()) {
                            t = SizeUtil.getScreenHeight(context) - v.getHeight();
                        }
                        if (l >= SizeUtil.getScreenWith(context) - v.getWidth()) {
                            l = SizeUtil.getScreenWith(context) - v.getWidth();
                        }
                        if (l <= 0) {
                            l = 0;
                        }
                        layoutParams.leftMargin = l;
                        layoutParams.topMargin = t;
                        v.setLayoutParams(layoutParams);
                        //5.更新开始的坐标
                        startX = newX;
                        startY = newY;
                        AppBus.getInstance().post(new BusEventData(v.getLeft(),v.getTop(),v.getLeft()+v.getWidth(),v.getTop()+v.getHeight(),
                                MotionEvent.ACTION_MOVE,view));
                        break;
                    case MotionEvent.ACTION_UP:
                        AppBus.getInstance().post(new BusEventData(v.getLeft(),v.getTop(),v.getLeft()+v.getWidth(),v.getTop()+v.getHeight(),
                                MotionEvent.ACTION_UP,view));
                        break;
                }
                return false;
            }
        });
    }


}
