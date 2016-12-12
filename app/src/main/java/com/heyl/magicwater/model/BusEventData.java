package com.heyl.magicwater.model;

import android.view.View;

/**
 * Created by heyl on 2016/10/26.
 */
public class BusEventData {


    private int left,top,right,bottom,action;
    private View mView;

    public BusEventData(int left, int top, int right, int bottom, int action, View view) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.action = action;
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
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

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
