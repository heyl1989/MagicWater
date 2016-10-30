package com.heyl.magicwater.model;

import com.heyl.magicwater.view.CustomSurfaceView;
import com.heyl.magicwater.view.CustomView;

/**
 * Created by heyl on 2016/10/26.
 */
public class BusEventData {

    private CustomView mCustomView;
    private CustomSurfaceView mCustomSurfaceView;
    private int left,top,right,bottom;
    private boolean isOriginal;

    public BusEventData(CustomView cv,int left,int top ,int right,int bottom,boolean isOriginal) {
        this.mCustomView = cv;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.isOriginal = isOriginal;
    }

    public BusEventData(CustomSurfaceView cv, int left, int top , int right, int bottom, boolean isOriginal) {
        this.mCustomSurfaceView = cv;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.isOriginal = isOriginal;
    }

    public CustomView getCustomView() {
        return mCustomView;
    }

    public CustomSurfaceView getCustomSurfaceView() {
        return mCustomSurfaceView;
    }

    public void setCustomSurfaceView(CustomSurfaceView customSurfaceView) {
        mCustomSurfaceView = customSurfaceView;
    }

    public void setCustomView(CustomView customView) {
        mCustomView = customView;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }
}
