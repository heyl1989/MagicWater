package com.heyl.magicwater.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heyl on 2016/11/11.
 */

public class GameDataModel implements Serializable{

    private int top ;
    private int lefe;
    private int right;
    private int bestStep;
    private int needWater;
    private int needBottle;

    public GameDataModel(int top, int lefe, int right, int bestStep, int needWater, int needBottle) {
        this.top = top;
        this.lefe = lefe;
        this.right = right;
        this.bestStep = bestStep;
        this.needWater = needWater;
        this.needBottle = needBottle;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLefe() {
        return lefe;
    }

    public void setLefe(int lefe) {
        this.lefe = lefe;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBestStep() {
        return bestStep;
    }

    public void setBestStep(int bestStep) {
        this.bestStep = bestStep;
    }

    public int getNeedWater() {
        return needWater;
    }

    public void setNeedWater(int needWater) {
        this.needWater = needWater;
    }

    public int getNeedBottle() {
        return needBottle;
    }

    public void setNeedBottle(int needBottle) {
        this.needBottle = needBottle;
    }
}
