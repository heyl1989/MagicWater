package com.heyl.magicwater.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

import com.heyl.magicwater.comman.Content;

/**
 * Created by heyl on 2016/10/26.
 */
public class SizeUtil {

    /**
     * 获取水流的宽
     * @param v
     */
    public static void setOutView(final View v){
        ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                v.getHeight();
                SPCash.saveData(Content.WATER_WIDTH,v.getWidth());
            }
        });

    }

    /**
     * 获取View的宽高
     */
    private static int[] hOrW = new int[4];
    public static void getHorW(final View v ,final SizeInterface sizeInterface ){
        ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hOrW[0] = v.getLeft();
                hOrW[1] = v.getTop();
                hOrW[2] = v.getRight();
                hOrW[3] = v.getBottom();
                sizeInterface.getViewWH(hOrW);
            }
        });
    }

    /**
     * 获取屏幕宽
     * @param context
     * @return
     */
    public static int getScreenWith(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenWidth;
    }

    /**
     * 获取屏幕高
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
