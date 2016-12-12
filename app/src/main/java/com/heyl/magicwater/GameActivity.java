package com.heyl.magicwater;

import android.animation.Animator;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heyl.magicwater.App.AppBus;
import com.heyl.magicwater.base.BaseActivity;
import com.heyl.magicwater.comman.Content;
import com.heyl.magicwater.model.BusEventData;
import com.heyl.magicwater.model.EasyData;
import com.heyl.magicwater.model.GameDataModel;
import com.heyl.magicwater.util.AnimateUtil;
import com.heyl.magicwater.util.BottleTouchUtil;
import com.heyl.magicwater.util.SPCash;
import com.heyl.magicwater.util.ShareUtil;
import com.heyl.magicwater.util.SizeInterface;
import com.heyl.magicwater.util.SizeUtil;
import com.heyl.magicwater.view.AnimImageView;
import com.heyl.magicwater.view.GameHelpDialog;
import com.heyl.magicwater.view.GameOverDialog;
import com.heyl.magicwater.view.SelectMenuPopwindow;
import com.squareup.otto.Subscribe;

import java.util.Arrays;

public class GameActivity extends BaseActivity {

    private TextView time, step, title, tvBottleOne, tvBottleTwo, tvBottleThree;
    private int stepNumber = 0, oneVolume, twoVolume, threeVolume;
    private int oneLeft = 0, oneTop = 0, oneRight = 0, oneBottom = 0;
    private int oneOriginalLeft = 0, oneOriginalTop = 0, oneOriginalRight = 0, oneOriginalBottom = 0;
    private int twoLeft = 0, twoTop = 0, twoRight = 0, twoBottom = 0;
    private int twoOriginalLeft = 0, twoOriginalTop = 0, twoOriginalRight = 0, twoOriginalBottom = 0;
    private int threeLeft = 0, threeTop = 0, threeRight = 0, threeBottom = 0;
    private int threeOriginalLeft = 0, threeOriginalTop = 0, threeOriginalRight = 0, threeOriginalBottom = 0;
    private int[] bottle = new int[]{R.drawable.bottle_0, R.drawable.bottle_1, R.drawable.bottle_2, R.drawable.bottle_3, R.drawable.bottle_4,
            R.drawable.bottle_5, R.drawable.bottle_6, R.drawable.bottle_7, R.drawable.bottle_8, R.drawable.bottle_9, R.drawable.bottle_10};
    private AnimImageView animImageView;
    private FrameLayout bottleOne, bottleTwo, bottleThree;
    private ImageView imgBottleOne, imgBottleTwo, imgBottleThree, imgBottleOneSide, imgBottleTwoSide, imgBottleThreeSide;
    private float correct = 0.6f;//缩放瓶子用的修正系数

    Handler handler = new Handler();
    private GameDataModel gameData;
    private int alreadyVolumOne, alreadyVolumTwo, alreadyVolumThree;
    private String grade;
    private int level;
    private RelativeLayout parent;

    @Override
    protected void initView() {
        AppBus.getInstance().register(this);
        setContentView(R.layout.activity_game);
        parent = (RelativeLayout) findViewById(R.id.rl_parent);

        //倒计时
        time = (TextView) findViewById(R.id.tv_time);
        time.setVisibility(View.GONE);
        //步数
        step = (TextView) findViewById(R.id.tv_step);
        //游戏题目
        title = (TextView) findViewById(R.id.tv_title);

        //传入水柱计算宽度
        ImageView animateImg = (ImageView) findViewById(R.id.img_animate);
        SizeUtil.setOutView(animateImg);
//        animateImg.setBackgroundResource(R.drawable.animation_water);
//        AnimationDrawable frameAnimation = (AnimationDrawable) animateImg.getBackground();
//        frameAnimation.start();
        animImageView = new AnimImageView();
        animImageView.setAnimation(animateImg);
        animImageView.start(true, 200);

        //第一个瓶子
        bottleOne = (FrameLayout) findViewById(R.id.fl_bottle_one);
        imgBottleOne = (ImageView) findViewById(R.id.cv_bottle_one);
        imgBottleOneSide = (ImageView) findViewById(R.id.img_bottle_one_side);
        tvBottleOne = (TextView) findViewById(R.id.tv_bottle_one);

        //第二个瓶子
        bottleTwo = (FrameLayout) findViewById(R.id.fl_bottle_two);
        imgBottleTwo = (ImageView) findViewById(R.id.cv_bottle_two);
        imgBottleTwoSide = (ImageView) findViewById(R.id.img_bottle_two_side);
        tvBottleTwo = (TextView) findViewById(R.id.tv_bottle_two);

        //第三个瓶子
        bottleThree = (FrameLayout) findViewById(R.id.fl_bottle_three);
        imgBottleThree = (ImageView) findViewById(R.id.cv_bottle_three);
        imgBottleThreeSide = (ImageView) findViewById(R.id.img_bottle_three_side);
        tvBottleThree = (TextView) findViewById(R.id.tv_bottle_three);

        //返回按钮
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMusic();
                finish();
            }
        });
        //选择按钮
        Button select = (Button) findViewById(R.id.btn_select);
        initBottomMenu(select);

        //添加点击事件
        BottleTouchUtil.changedLocation(this, bottleOne);
        BottleTouchUtil.changedLocation(this, bottleTwo);
        BottleTouchUtil.changedLocation(this, bottleThree);
        //初始布局位置
        initOriginalPosition();
        //双击事件
        initDoubleClik(bottleOne);
        initDoubleClik(bottleTwo);
        initDoubleClik(bottleThree);

        Bundle bundle = getIntent().getExtras();
        gameData = (GameDataModel) bundle.getSerializable("gamedata");
        grade = bundle.getString("grade");
        level = bundle.getInt("level");
        initData();

    }

    /**
     * 底部按钮
     */
    private void initBottomMenu(final Button select) {
        final SelectMenuPopwindow selectMenuPopwindow = new SelectMenuPopwindow(this);
        selectMenuPopwindow.setOnCommentPopupClickListener(new SelectMenuPopwindow.OnCommentPopupClickListener() {
            @Override
            public void onMenuClick() {
                clickMusic();
                selectMenuPopwindow.dissmiss();
                finish();
            }

            @Override
            public void onReStartClick() {
                clickMusic();
                resetData();
            }

            @Override
            public void onRegulationClick() {
                clickMusic();
                GameHelpDialog.show(GameActivity.this);
            }

            @Override
            public void onPopDismiss() {
                AnimateUtil.rotate(select, -90f, 0f);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMusic();
                AnimateUtil.rotate(select, 0f, -90f);
                selectMenuPopwindow.showPopupWindow(select);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        title.setText("向" + gameData.getNeedBottle() + "L的瓶装入" + gameData.getNeedWater() + "L的水");
        if (gameData.getTop() == 0) {
            bottleOne.setVisibility(View.INVISIBLE);
            tvBottleOne.setVisibility(View.INVISIBLE);
        } else {
            bottleOne.setVisibility(View.VISIBLE);
            tvBottleOne.setVisibility(View.VISIBLE);
            tvBottleOne.setText("0/" + gameData.getTop());
        }
        step.setText("0");
        tvBottleTwo.setText("0/" + gameData.getLefe());
        tvBottleThree.setText("0/" + gameData.getRight());
        oneVolume = gameData.getTop();
        twoVolume = gameData.getLefe();
        threeVolume = gameData.getRight();
        zoomBottle(bottleOne);
        zoomBottle(bottleTwo);
        zoomBottle(bottleThree);
        //初始化瓶子大小代码
        int[] arr = new int[]{gameData.getTop(), gameData.getLefe(), gameData.getRight()};
        if (gameData.getTop() != 0 && gameData.getTop() < getMax(arr)) {
            float zoom = (float) gameData.getTop() / getMax(arr) + correct - (gameData.getTop() * correct) / getMax(arr);
            zoomBottle(bottleOne, zoom);
        }
        if (gameData.getLefe() != 0 && gameData.getLefe() < getMax(arr)) {
            float zoom = (float) gameData.getLefe() / getMax(arr) + correct - (gameData.getLefe() * correct) / getMax(arr);
            zoomBottle(bottleTwo, zoom);
        }
        if (gameData.getRight() != 0 && gameData.getRight() < getMax(arr)) {
            float zoom = (float) gameData.getRight() / getMax(arr) + correct - (gameData.getRight() * correct) / getMax(arr);
            zoomBottle(bottleThree, zoom);
        }
        resetData();
    }

    /**
     * 复位数据
     */
    private void resetData() {
        stepNumber = 0;
        step.setText("0");
        alreadyVolumOne = 0;
        alreadyVolumTwo = 0;
        alreadyVolumThree = 0;
        imgBottleOne.setImageResource(bottle[0]);
        imgBottleOne.setTag(0);
        tvBottleOne.setText("0/" + oneVolume);
        imgBottleTwo.setImageResource(bottle[0]);
        imgBottleTwo.setTag(0);
        tvBottleTwo.setText("0/" + twoVolume);
        imgBottleThree.setImageResource(bottle[0]);
        imgBottleThree.setTag(0);
        tvBottleThree.setText("0/" + threeVolume);
    }

    /**
     * @param arr
     */
    private int getMax(int[] arr) {
        Arrays.sort(arr);
        return arr[arr.length - 1];
    }

    /**
     * 初始化瓶子大小
     */
    private void zoomBottle(FrameLayout frame, float zoom) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frame.getLayoutParams();
        layoutParams.width = (int) (zoom * dp2px(108));
        layoutParams.height = (int) (zoom * dp2px(198));
        frame.setLayoutParams(layoutParams);
        for (int i = 0; i < frame.getChildCount(); i++) {
            FrameLayout.LayoutParams frameLayoutParams = (FrameLayout.LayoutParams) frame.getChildAt(i).getLayoutParams();
            frameLayoutParams.width = (int) (zoom * dp2px(108));
            frameLayoutParams.height = (int) (zoom * dp2px(198));
            frame.getChildAt(i).setLayoutParams(frameLayoutParams);
        }
    }

    /**
     * 恢复瓶子大小到原来
     */
    private void zoomBottle(FrameLayout frame) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frame.getLayoutParams();
        layoutParams.width = dp2px(108);
        layoutParams.height = dp2px(198);
        frame.setLayoutParams(layoutParams);
        for (int i = 0; i < frame.getChildCount(); i++) {
            FrameLayout.LayoutParams frameLayoutParams = (FrameLayout.LayoutParams) frame.getChildAt(i).getLayoutParams();
            frameLayoutParams.width = dp2px(108);
            frameLayoutParams.height = dp2px(198);
            frame.getChildAt(i).setLayoutParams(frameLayoutParams);
        }
    }


    /**
     * 读取三个瓶子原始位置
     */
    private void initOriginalPosition() {
        SizeUtil.getHorW(bottleOne, new SizeInterface() {
            @Override
            public void getViewWH(int[] wh) {
                oneOriginalLeft = wh[0];
                oneOriginalTop = wh[1];
                oneOriginalRight = wh[2];
                oneOriginalBottom = wh[3];
            }
        });
        SizeUtil.getHorW(bottleTwo, new SizeInterface() {
            @Override
            public void getViewWH(int[] wh) {
                twoOriginalLeft = wh[0];
                twoOriginalTop = wh[1];
                twoOriginalRight = wh[2];
                twoOriginalBottom = wh[3];
            }
        });
        SizeUtil.getHorW(bottleThree, new SizeInterface() {
            @Override
            public void getViewWH(int[] wh) {
                threeOriginalLeft = wh[0];
                threeOriginalTop = wh[1];
                threeOriginalRight = wh[2];
                threeOriginalBottom = wh[3];
            }
        });
    }

    /**
     * 接收事件总线数据
     */
    @Subscribe
    public void onMyScrollChange(BusEventData data) {
        switch (data.getAction()) {
            case Content.ACTION_DOWN:
                whichBottleDown(data);
                break;
            case Content.ACTION_MOVE:
                whichBottleMove(data);
                break;
            case Content.ACTION_UP:
                whichBottleUp(data);
                break;
        }
    }

    /**
     * 被按下的瓶子
     *
     * @param data
     */
    private void whichBottleDown(BusEventData data) {
        FrameLayout frameLayout = (FrameLayout) data.getView();
        if (frameLayout.equals(bottleOne)) {
            bottleOne.bringToFront();
            imgBottleOneSide.setImageResource(R.drawable.bottle_down);
        }
        if (frameLayout.equals(bottleTwo)) {
            bottleTwo.bringToFront();
            imgBottleTwoSide.setImageResource(R.drawable.bottle_down);
        }
        if (frameLayout.equals(bottleThree)) {
            bottleThree.bringToFront();
            imgBottleThreeSide.setImageResource(R.drawable.bottle_down);
        }
    }

    /**
     * 移动的瓶子
     *
     * @param data
     */
    private void whichBottleMove(BusEventData data) {
        FrameLayout frameLayout = (FrameLayout) data.getView();
        //第一个瓶子
        if (frameLayout.equals(bottleOne)) {
            oneLeft = data.getLeft();
            oneTop = data.getTop();
            oneRight = data.getRight();
            oneBottom = data.getBottom();
            //移动到第二个瓶子附近
            if (ifOneInTwo()) {
                imgBottleTwoSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleTwoSide.setImageResource(0);
            }
            //移动到第三个瓶子附近
            if (ifOneInThree()) {
                imgBottleThreeSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleThreeSide.setImageResource(0);
            }
        }
        //第二个瓶子
        if (frameLayout.equals(bottleTwo)) {
            twoLeft = data.getLeft();
            twoTop = data.getTop();
            twoRight = data.getRight();
            twoBottom = data.getBottom();
            //移动到第一个瓶子附近
            if (ifTwoInOne()) {
                imgBottleOneSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleOneSide.setImageResource(0);
            }
            //移动到第三个瓶子附近
            if (ifTwoInThree()) {
                imgBottleThreeSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleThreeSide.setImageResource(0);
            }
        }
        //第三个瓶子
        if (frameLayout.equals(bottleThree)) {
            threeLeft = data.getLeft();
            threeTop = data.getTop();
            threeRight = data.getRight();
            threeBottom = data.getBottom();
            //移动到第一个瓶子附近
            if (ifThreeInOne()) {
                imgBottleOneSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleOneSide.setImageResource(0);
            }
            //移动到第二个瓶子附近
            if (ifThreeInTwo()) {
                imgBottleTwoSide.setImageResource(R.drawable.bottle_drop);
            } else {
                imgBottleTwoSide.setImageResource(0);
            }
        }
    }

    /**
     * 手指抬起的瓶子
     *
     * @param data
     */
    private void whichBottleUp(BusEventData data) {
        int waterWidth = SPCash.getData(Content.WATER_WIDTH, 0);
        FrameLayout frameLayout = (FrameLayout) data.getView();

        //第一个瓶子
        if (frameLayout.equals(bottleOne)) {
            //去掉蓝色边
            imgBottleOneSide.setImageResource(0);
            if (ifOneInTwo()) {
                //1在第二个瓶子范围
                oneIntwo();
            } else if (ifOneInThree()) {
                //1在第三个瓶子范围内
                oneInThreee();
            } else if (oneLeft < waterWidth && getTag(imgBottleOne) != 10) {
                groundWater();//接水的声音
                imgBottleOne.setImageResource(bottle[10]);
                imgBottleOne.setTag(10);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //瓶中水量
                tvBottleOne.setText(gameData.getTop() + "/" + gameData.getTop());
                alreadyVolumOne = gameData.getTop();
                //等待一秒回原来的位置
                delayedToOriginalPositon(bottleOne);
            } else {
                toOriginalPosition(bottleOne);
            }
            //判断是不是1自己需要装水成功
            if (gameData.getNeedBottle() == oneVolume && alreadyVolumOne == gameData.getNeedWater()) {
                gameOverDialog();
            }
        }

        //第二个瓶子
        if (frameLayout.equals(bottleTwo)) {
            //去掉蓝色边
            imgBottleTwoSide.setImageResource(0);
            if (ifTwoInOne()) {
                //2在第一个瓶子范围内
                twoInOne();
            } else if (ifTwoInThree()) {
                //2在第三个瓶子范围内
                twoInThree();
            } else if (twoLeft < waterWidth && getTag(imgBottleTwo) != 10) {
                groundWater();//接水的声音
                //在水柱范围内
                imgBottleTwo.setImageResource(bottle[10]);
                imgBottleTwo.setTag(10);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //瓶中水量
                tvBottleTwo.setText(gameData.getLefe() + "/" + gameData.getLefe());
                alreadyVolumTwo = gameData.getLefe();
                //等待一秒回原来的位置
                delayedToOriginalPositon(bottleTwo);
            } else {
                toOriginalPosition(bottleTwo);
            }
            //判断是不是2自己需要装水成功
            if (gameData.getNeedBottle() == twoVolume && alreadyVolumTwo == gameData.getNeedWater()) {
                gameOverDialog();
            }
        }

        //第三个瓶子
        if (frameLayout.equals(bottleThree)) {
            //去掉蓝色边
            imgBottleThreeSide.setImageResource(0);
            if (ifThreeInOne()) {
                //3在第一个瓶子范围内
                threeInOne();
            } else if (ifThreeInTwo()) {
                //3在第二个瓶子范围
                threeInTwo();
            } else if (threeLeft < waterWidth && getTag(imgBottleThree) != 10) {
                groundWater();//接水的声音
                imgBottleThree.setImageResource(bottle[10]);
                imgBottleThree.setTag(10);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //瓶中水量
                tvBottleThree.setText(gameData.getRight() + "/" + gameData.getRight());
                alreadyVolumThree = threeVolume;
                //等待一秒回原来的位置
                delayedToOriginalPositon(bottleThree);
            } else {
                toOriginalPosition(bottleThree);
            }
            //判断是不是3自己需要装水成功
            if (gameData.getNeedBottle() == threeVolume && alreadyVolumThree == gameData.getNeedWater()) {
                gameOverDialog();
            }
        }
    }

    private int getTag(ImageView imgBottle) {
        if (null != imgBottle.getTag()) {
            return (int) imgBottle.getTag();
        } else {
            return 0;
        }
    }

    /**
     * 1在2的范围内
     */
    private void oneIntwo() {
        //松手倒水逻辑
        try {
            int imgBottleOneTag;
            int imgBottleTwoTag;
            if (null == imgBottleOne.getTag()) {
                imgBottleOneTag = 0;
            } else {
                imgBottleOneTag = (int) imgBottleOne.getTag();
            }
            if (null == imgBottleTwo.getTag()) {
                imgBottleTwoTag = 0;
            } else {
                imgBottleTwoTag = (int) imgBottleTwo.getTag();
            }
            if (imgBottleOneTag != 0 && imgBottleTwoTag != 10) {
                //倒水的声音
                pourWater();
                //瓶子2里面已经有的水
                //alreadyVolumTwo = Math.round(twoVolume*imgBottleTwoTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子2剩余容积
                int restVolumtwo = twoVolume - alreadyVolumTwo;
                //瓶子1里面装的水
                //alreadyVolumOne = Math.round(oneVolume*imgBottleOneTag/10.0f);
                //如果1里面的水正好等于2剩余的容积
                if (alreadyVolumOne - restVolumtwo == 0) {
                    imgBottleOne.setImageResource(bottle[0]);
                    imgBottleOne.setTag(0);
                    tvBottleOne.setText("0/" + oneVolume);
                    alreadyVolumOne = 0;

                    imgBottleTwo.setImageResource(bottle[10]);
                    imgBottleTwo.setTag(10);
                    tvBottleTwo.setText(twoVolume + "/" + twoVolume);
                    alreadyVolumTwo = twoVolume;
                }
                //如果1里面的水小于2剩余的容积
                if (alreadyVolumOne - restVolumtwo < 0) {
                    imgBottleOne.setImageResource(bottle[0]);
                    imgBottleOne.setTag(0);
                    tvBottleOne.setText("0/" + oneVolume);

                    alreadyVolumTwo += alreadyVolumOne;
                    alreadyVolumOne = 0;

                    int tag = Math.round(10.0f * alreadyVolumTwo / twoVolume);
                    imgBottleTwo.setImageResource(bottle[tag]);
                    imgBottleTwo.setTag(tag);
                    tvBottleTwo.setText(alreadyVolumTwo + "/" + twoVolume);
                }
                //如果1里面的水大于2剩余的容积
                if (alreadyVolumOne - restVolumtwo > 0) {

                    alreadyVolumOne -= restVolumtwo;
                    alreadyVolumTwo = twoVolume;

                    int tag = Math.round(10.0f * alreadyVolumOne / oneVolume);
                    imgBottleOne.setImageResource(bottle[tag]);
                    imgBottleOne.setTag(tag);
                    tvBottleOne.setText(alreadyVolumOne + "/" + oneVolume);

                    imgBottleTwo.setImageResource(bottle[10]);
                    imgBottleTwo.setTag(10);
                    tvBottleTwo.setText(twoVolume + "/" + twoVolume);
                }
                //延时回原来的位置
                imgBottleTwoSide.setImageResource(0);
                delayedToOriginalPositon(bottleOne);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == twoVolume && alreadyVolumTwo == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleTwoSide.setImageResource(0);
                toOriginalPosition(bottleOne);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1在3范围
     */
    private void oneInThreee() {
        //松手倒水逻辑
        try {
            int imgBottleOneTag;
            int imgBottleThreeTag;
            if (null == imgBottleOne.getTag()) {
                imgBottleOneTag = 0;
            } else {
                imgBottleOneTag = (int) imgBottleOne.getTag();
            }
            if (null == imgBottleThree.getTag()) {
                imgBottleThreeTag = 0;
            } else {
                imgBottleThreeTag = (int) imgBottleThree.getTag();
            }
            if (imgBottleOneTag != 0 && imgBottleThreeTag != 10) {
                //倒水的声音
                pourWater();
                //瓶子3里面已经有的水
                //alreadyVolumThree = Math.round(threeVolume*imgBottleThreeTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子3剩余容积
                int restVolumThree = threeVolume - alreadyVolumThree;
                //瓶子1里面装的水
                //alreadyVolumOne = Math.round(oneVolume*imgBottleOneTag/10.0f);
                //如果1里面的水正好等于3剩余的容积
                if (alreadyVolumOne - restVolumThree == 0) {
                    imgBottleOne.setImageResource(bottle[0]);
                    imgBottleOne.setTag(0);
                    tvBottleOne.setText("0/" + oneVolume);
                    alreadyVolumOne = 0;

                    imgBottleThree.setImageResource(bottle[10]);
                    imgBottleThree.setTag(10);
                    tvBottleThree.setText(threeVolume + "/" + threeVolume);
                    alreadyVolumThree = threeVolume;
                }
                //如果1里面的水小于3剩余的容积
                if (alreadyVolumOne - restVolumThree < 0) {
                    imgBottleOne.setImageResource(bottle[0]);
                    imgBottleOne.setTag(0);
                    tvBottleOne.setText("0/" + oneVolume);

                    alreadyVolumThree += alreadyVolumOne;
                    alreadyVolumOne = 0;

                    int tag = Math.round(10.0f * alreadyVolumThree / threeVolume);
                    imgBottleThree.setImageResource(bottle[tag]);
                    imgBottleThree.setTag(tag);
                    tvBottleThree.setText(alreadyVolumThree + "/" + threeVolume);
                }
                //如果1里面的水大于3剩余的容积
                if (alreadyVolumOne - restVolumThree > 0) {
                    alreadyVolumOne -= restVolumThree;
                    alreadyVolumThree = threeVolume;

                    int tag = Math.round(10.0f * alreadyVolumOne / oneVolume);
                    imgBottleOne.setImageResource(bottle[tag]);
                    imgBottleOne.setTag(tag);
                    tvBottleOne.setText(alreadyVolumOne + "/" + oneVolume);

                    imgBottleThree.setImageResource(bottle[10]);
                    imgBottleThree.setTag(10);
                    tvBottleThree.setText(threeVolume + "/" + threeVolume);
                }
                //延时回原来的位置
                imgBottleThreeSide.setImageResource(0);
                delayedToOriginalPositon(bottleOne);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == threeVolume && alreadyVolumThree == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleThreeSide.setImageResource(0);
                toOriginalPosition(bottleOne);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2在1范围
     */
    private void twoInOne() {
        //松手倒水逻辑
        try {
            int imgBottleTwoTag;
            int imgBottleOneTag;
            if (null == imgBottleTwo.getTag()) {
                imgBottleTwoTag = 0;
            } else {
                imgBottleTwoTag = (int) imgBottleTwo.getTag();
            }
            if (null == imgBottleOne.getTag()) {
                imgBottleOneTag = 0;
            } else {
                imgBottleOneTag = (int) imgBottleOne.getTag();
            }
            if (imgBottleTwoTag != 0 && imgBottleOneTag != 10) {
                //倒水的声音
                pourWater();
                //瓶子1里面已经有的水
                //alreadyVolumOne = Math.round(oneVolume*imgBottleOneTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子1剩余容积
                int restVolumone = oneVolume - alreadyVolumOne;
                //瓶子2里面装的水
                //alreadyVolumTwo = Math.round(twoVolume*imgBottleTwoTag/10.0f);
                //如果2里面的水正好等于1剩余的容积
                if (alreadyVolumTwo - restVolumone == 0) {
                    imgBottleTwo.setImageResource(bottle[0]);
                    imgBottleTwo.setTag(0);
                    tvBottleTwo.setText("0/" + twoVolume);
                    alreadyVolumTwo = 0;

                    imgBottleOne.setImageResource(bottle[10]);
                    imgBottleOne.setTag(10);
                    tvBottleOne.setText(oneVolume + "/" + oneVolume);
                    alreadyVolumOne = oneVolume;
                }
                //如果2里面的水小于1剩余的容积
                if (alreadyVolumTwo - restVolumone < 0) {
                    imgBottleTwo.setImageResource(bottle[0]);
                    imgBottleTwo.setTag(0);
                    tvBottleTwo.setText("0/" + twoVolume);

                    alreadyVolumOne += alreadyVolumTwo;
                    alreadyVolumTwo = 0;

                    int tag = Math.round(10.0f * alreadyVolumOne / oneVolume);
                    imgBottleOne.setImageResource(bottle[tag]);
                    imgBottleOne.setTag(tag);
                    tvBottleOne.setText(alreadyVolumOne + "/" + oneVolume);
                }
                //如果2里面的水大于1剩余的容积
                if (alreadyVolumTwo - restVolumone > 0) {
                    alreadyVolumTwo -= restVolumone;
                    alreadyVolumOne = oneVolume;

                    int tag = Math.round(10.0f * alreadyVolumTwo / twoVolume);
                    imgBottleTwo.setImageResource(bottle[tag]);
                    imgBottleTwo.setTag(tag);
                    tvBottleTwo.setText(alreadyVolumTwo + "/" + twoVolume);

                    imgBottleOne.setImageResource(bottle[10]);
                    imgBottleOne.setTag(10);
                    tvBottleOne.setText(oneVolume + "/" + oneVolume);
                }
                //延时回原来的位置
                imgBottleOneSide.setImageResource(0);
                delayedToOriginalPositon(bottleTwo);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == oneVolume && alreadyVolumOne == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleOneSide.setImageResource(0);
                toOriginalPosition(bottleTwo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2在3范围
     */
    private void twoInThree() {
        //松手倒水逻辑
        try {
            int imgBottleTwoTag;
            int imgBottleThreeTag;
            if (null == imgBottleTwo.getTag()) {
                imgBottleTwoTag = 0;
            } else {
                imgBottleTwoTag = (int) imgBottleTwo.getTag();
            }
            if (null == imgBottleThree.getTag()) {
                imgBottleThreeTag = 0;
            } else {
                imgBottleThreeTag = (int) imgBottleThree.getTag();
            }
            if (imgBottleTwoTag != 0 && imgBottleThreeTag != 10) {

                //倒水的声音
                pourWater();
                //瓶子3里面已经有的水
                //alreadyVolumThree = Math.round(threeVolume*imgBottleThreeTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子3剩余容积
                int restVolumThree = threeVolume - alreadyVolumThree;
                //瓶子2里面装的水
                //alreadyVolumTwo = Math.round(twoVolume*imgBottleTwoTag/10.0f);
                //如果2里面的水正好等于3剩余的容积
                if (alreadyVolumTwo - restVolumThree == 0) {

                    imgBottleTwo.setImageResource(bottle[0]);
                    imgBottleTwo.setTag(0);
                    tvBottleTwo.setText("0/" + twoVolume);
                    alreadyVolumTwo = 0;

                    imgBottleThree.setImageResource(bottle[10]);
                    imgBottleThree.setTag(10);
                    tvBottleThree.setText(threeVolume + "/" + threeVolume);
                    alreadyVolumThree = threeVolume;
                }
                //如果2里面的水小于3剩余的容积
                if (alreadyVolumTwo - restVolumThree < 0) {
                    imgBottleTwo.setImageResource(bottle[0]);
                    imgBottleTwo.setTag(0);
                    tvBottleTwo.setText("0/" + twoVolume);

                    alreadyVolumThree += alreadyVolumTwo;
                    alreadyVolumTwo = 0;

                    int tag = Math.round(10.0f * alreadyVolumThree / threeVolume);
                    imgBottleThree.setImageResource(bottle[tag]);
                    imgBottleThree.setTag(tag);
                    tvBottleThree.setText(alreadyVolumThree + "/" + threeVolume);
                }
                //如果2里面的水大于3剩余的容积
                if (alreadyVolumTwo - restVolumThree > 0) {
                    alreadyVolumTwo -= restVolumThree;
                    alreadyVolumThree = threeVolume;

                    int tag = Math.round(10.0f * alreadyVolumTwo / twoVolume);
                    imgBottleTwo.setImageResource(bottle[tag]);
                    imgBottleTwo.setTag(tag);
                    tvBottleTwo.setText(alreadyVolumTwo + "/" + twoVolume);

                    imgBottleThree.setImageResource(bottle[10]);
                    imgBottleThree.setTag(10);
                    tvBottleThree.setText(threeVolume + "/" + threeVolume);
                }
                //延时回原来的位置
                imgBottleThreeSide.setImageResource(0);
                delayedToOriginalPositon(bottleTwo);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == threeVolume && alreadyVolumThree == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleThreeSide.setImageResource(0);
                toOriginalPosition(bottleTwo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3在1范围
     */
    private void threeInOne() {
        //松手倒水逻辑
        try {
            int imgBottleThreeTag;
            int imgBottleOneTag;
            if (null == imgBottleThree.getTag()) {
                imgBottleThreeTag = 0;
            } else {
                imgBottleThreeTag = (int) imgBottleThree.getTag();
            }
            if (null == imgBottleOne.getTag()) {
                imgBottleOneTag = 0;
            } else {
                imgBottleOneTag = (int) imgBottleOne.getTag();
            }
            if (imgBottleThreeTag != 0 && imgBottleOneTag != 10) {

                //倒水的声音
                pourWater();
                //瓶子1里面已经有的水
                //alreadyVolumOne = Math.round(oneVolume*imgBottleOneTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子1剩余容积
                int restVolumone = oneVolume - alreadyVolumOne;
                //瓶子3里面装的水
                //alreadyVolumThree = Math.round(threeVolume*imgBottleThreeTag/10.0f);
                //如果3里面的水正好等于1剩余的容积
                if (alreadyVolumThree - restVolumone == 0) {

                    imgBottleThree.setImageResource(bottle[0]);
                    imgBottleThree.setTag(0);
                    tvBottleThree.setText("0/" + threeVolume);
                    alreadyVolumThree = 0;

                    imgBottleOne.setImageResource(bottle[10]);
                    imgBottleOne.setTag(10);
                    tvBottleOne.setText(oneVolume + "/" + oneVolume);
                    alreadyVolumOne = oneVolume;
                }
                //如果3里面的水小于1剩余的容积
                if (alreadyVolumThree - restVolumone < 0) {
                    imgBottleThree.setImageResource(bottle[0]);
                    imgBottleThree.setTag(0);
                    tvBottleThree.setText("0/" + threeVolume);

                    alreadyVolumOne += alreadyVolumThree;
                    alreadyVolumThree = 0;

                    int tag = Math.round(10.0f * alreadyVolumOne / oneVolume);
                    imgBottleOne.setImageResource(bottle[tag]);
                    imgBottleOne.setTag(tag);
                    tvBottleOne.setText(alreadyVolumOne + "/" + oneVolume);
                }
                //如果3里面的水大于1剩余的容积
                if (alreadyVolumThree - restVolumone > 0) {
                    alreadyVolumThree -= restVolumone;
                    alreadyVolumOne = oneVolume;

                    int tag = Math.round(10.0f * alreadyVolumThree / threeVolume);
                    imgBottleThree.setImageResource(bottle[tag]);
                    imgBottleThree.setTag(tag);
                    tvBottleThree.setText(alreadyVolumThree + "/" + threeVolume);

                    imgBottleOne.setImageResource(bottle[10]);
                    imgBottleOne.setTag(10);
                    tvBottleOne.setText(oneVolume + "/" + oneVolume);
                }
                //延时回原来的位置
                imgBottleOneSide.setImageResource(0);
                delayedToOriginalPositon(bottleThree);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == oneVolume && alreadyVolumOne == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleOneSide.setImageResource(0);
                toOriginalPosition(bottleThree);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3在2范围
     */
    private void threeInTwo() {
        //松手倒水逻辑
        try {
            int imgBottleThreeTag;
            int imgBottleTwoTag;
            if (null == imgBottleThree.getTag()) {
                imgBottleThreeTag = 0;
            } else {
                imgBottleThreeTag = (int) imgBottleThree.getTag();
            }
            if (null == imgBottleTwo.getTag()) {
                imgBottleTwoTag = 0;
            } else {
                imgBottleTwoTag = (int) imgBottleTwo.getTag();
            }
            if (imgBottleThreeTag != 0 && imgBottleTwoTag != 10) {

                //倒水的声音
                pourWater();
                //瓶子2里面已经有的水
                //alreadyVolumTwo = Math.round(twoVolume*imgBottleTwoTag/10.0f);//Log.e("四舍五入",alreadyVolumThree+"");
                //瓶子2剩余容积
                int restVolumtwo = twoVolume - alreadyVolumTwo;
                //瓶子3里面装的水
                //alreadyVolumThree = Math.round(threeVolume*imgBottleThreeTag/10.0f);
                //如果3里面的水正好等于2剩余的容积
                if (alreadyVolumThree - restVolumtwo == 0) {
                    imgBottleThree.setImageResource(bottle[0]);
                    imgBottleThree.setTag(0);
                    tvBottleThree.setText("0/" + threeVolume);
                    alreadyVolumThree = 0;

                    imgBottleTwo.setImageResource(bottle[10]);
                    imgBottleTwo.setTag(10);
                    tvBottleTwo.setText(twoVolume + "/" + twoVolume);
                    alreadyVolumTwo = twoVolume;
                }
                //如果3里面的水小于2剩余的容积
                if (alreadyVolumThree - restVolumtwo < 0) {
                    imgBottleThree.setImageResource(bottle[0]);
                    imgBottleThree.setTag(0);
                    tvBottleThree.setText("0/" + threeVolume);

                    alreadyVolumTwo += alreadyVolumThree;
                    alreadyVolumThree = 0;

                    int tag = Math.round(10.0f * alreadyVolumTwo / twoVolume);
                    imgBottleTwo.setImageResource(bottle[tag]);
                    imgBottleTwo.setTag(tag);
                    tvBottleTwo.setText(alreadyVolumTwo + "/" + twoVolume);
                }
                //如果3里面的水大于2剩余的容积
                if (alreadyVolumThree - restVolumtwo > 0) {
                    alreadyVolumThree -= restVolumtwo;
                    alreadyVolumTwo = twoVolume;

                    int tag = Math.round(10.0f * alreadyVolumThree / threeVolume);
                    imgBottleThree.setImageResource(bottle[tag]);
                    imgBottleThree.setTag(tag);
                    tvBottleThree.setText(alreadyVolumThree + "/" + threeVolume);
                    imgBottleTwo.setImageResource(bottle[10]);
                    imgBottleTwo.setTag(10);
                    tvBottleTwo.setText(twoVolume + "/" + twoVolume);
                }
                //延时回原来的位置
                imgBottleTwoSide.setImageResource(0);
                delayedToOriginalPositon(bottleThree);
                //记步数
                stepNumber++;
                step.setText(stepNumber + "");
                //是否已经成功
                if (gameData.getNeedBottle() == twoVolume && alreadyVolumTwo == gameData.getNeedWater()) {
                    gameOverDialog();
                }
            } else {
                //如果不可以倒水，松手回到初始位置
                imgBottleTwoSide.setImageResource(0);
                toOriginalPosition(bottleThree);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延时回到原始位置
     *
     * @param view
     */
    private void delayedToOriginalPositon(final FrameLayout view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toOriginalPosition(view);
            }
        }, 1100);
    }

    /**
     * 回到原始位置
     *
     * @param frame
     */
    private void toOriginalPosition(FrameLayout frame) {
        //设置图片的位置
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frame.getLayoutParams();
        if (frame.equals(bottleOne)) {
            layoutParams.leftMargin = oneOriginalLeft;
            layoutParams.topMargin = oneOriginalTop;
        }
        if (frame.equals(bottleTwo)) {
            layoutParams.leftMargin = twoOriginalLeft;
            layoutParams.topMargin = twoOriginalTop;
        }
        if (frame.equals(bottleThree)) {
            layoutParams.leftMargin = threeOriginalLeft;
            layoutParams.topMargin = threeOriginalTop;
        }
        frame.setLayoutParams(layoutParams);
    }

    /**
     * 三个瓶子双击事件
     */
    private static long[] mHits = new long[2];

    private void initDoubleClik(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取离开机的时间
                if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                    //双击了
                    FrameLayout bottle = (FrameLayout) view;
                    Log.e("ID", (Integer) imgBottleOne.getTag() + "/" + R.drawable.bottle_0);
                    try {
                        if (bottle.equals(bottleOne) && (Integer) imgBottleOne.getTag() != 0) {
                            int x = (oneOriginalRight - oneOriginalLeft) / 2;
                            int y = (oneOriginalBottom - oneOriginalTop) / 2;
                            rotateAnimate(bottleOne, x, y);
                        }
                        if (bottle.equals(bottleTwo) && (Integer) imgBottleTwo.getTag() != 0) {
                            int x = (twoOriginalRight - twoOriginalLeft) / 2;
                            int y = (twoOriginalBottom - twoOriginalTop) / 2;
                            rotateAnimate(bottleTwo, x, y);
                        }
                        if (bottle.equals(bottleThree) && (Integer) imgBottleThree.getTag() != 0) {
                            int x = (threeOriginalRight - threeOriginalLeft) / 2;
                            int y = (threeOriginalBottom - threeOriginalTop) / 2;
                            rotateAnimate(bottleThree, x, y);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            private void rotateAnimate(final FrameLayout one, final int x, final int y) {
                AnimateUtil.rotate(view, x, y, 0f, 120f, new AnimateUtil.AnimationEndListener() {
                    @Override
                    public void animationEnd(Animator animation) {
                        stepNumber++;
                        step.setText(stepNumber + "");
                        FrameLayout bottleLayout = (FrameLayout) one;
                        if (bottleLayout.equals(bottleOne)) {
                            imgBottleOne.setImageResource(bottle[0]);
                            imgBottleOne.setTag(0);
                            //瓶中水量
                            tvBottleOne.setText("0/" + gameData.getTop());
                            alreadyVolumOne = 0;
                        }
                        if (bottleLayout.equals(bottleTwo)) {
                            imgBottleTwo.setImageResource(bottle[0]);
                            imgBottleTwo.setTag(0);
                            //瓶中水量
                            tvBottleTwo.setText("0/" + gameData.getLefe());
                            alreadyVolumTwo = 0;
                        }
                        if (bottleLayout.equals(bottleThree)) {
                            imgBottleThree.setImageResource(bottle[0]);
                            imgBottleThree.setTag(0);
                            //瓶中水量
                            tvBottleThree.setText("0/" + gameData.getRight());
                            alreadyVolumThree = 0;
                        }
                        AnimateUtil.rotate(view, x, y, 120f, 0f);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        AppBus.getInstance().unregister(this);
        animImageView.stop();
        super.onDestroy();
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return SizeUtil.dp2px(this, dp);
    }

    /**
     * 第一个瓶子是否进入第二个瓶子范围
     *
     * @return
     */
    private boolean ifOneInTwo() {
        if (oneLeft < (twoOriginalRight - twoOriginalLeft) / 2 + twoOriginalLeft
                && oneLeft > twoOriginalLeft
                && oneBottom < twoOriginalBottom
                && oneBottom > twoOriginalTop + (twoOriginalBottom - twoOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 第一个瓶子是否进入第三个瓶子范围
     *
     * @return
     */
    private boolean ifOneInThree() {
        if (oneRight < threeOriginalRight
                && oneRight > threeOriginalLeft + (threeOriginalRight - threeOriginalLeft) / 2
                && oneBottom < threeOriginalBottom
                && oneBottom > twoOriginalTop + (twoOriginalBottom - twoOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 第二个瓶子是否进入第一个瓶子范围
     *
     * @return
     */
    private boolean ifTwoInOne() {
        if (twoRight < oneOriginalRight
                && twoRight > oneOriginalLeft + (oneOriginalRight - oneOriginalLeft) / 2
                && twoBottom < oneOriginalBottom
                && twoBottom > oneOriginalTop + (oneOriginalBottom - oneOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 第二个瓶子是否进入第三个瓶子范围
     *
     * @return
     */
    private boolean ifTwoInThree() {
        if (twoRight < threeOriginalLeft + (threeOriginalRight - threeOriginalLeft) / 2
                && twoRight > threeOriginalLeft
                && twoBottom < threeOriginalBottom
                && twoBottom > threeOriginalTop + (threeOriginalBottom - threeOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 第三个瓶子是否进入第一个瓶子范围
     *
     * @return
     */
    private boolean ifThreeInOne() {
        if (threeLeft < oneOriginalLeft + (oneOriginalBottom - oneOriginalLeft) / 2
                && threeLeft > oneOriginalLeft
                && threeBottom < oneOriginalBottom
                && threeBottom > oneOriginalTop + (oneOriginalBottom - oneOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 第三个瓶子是否进入第二个瓶子范围
     *
     * @return
     */
    private boolean ifThreeInTwo() {
        if (threeLeft < twoOriginalRight
                && threeLeft > twoOriginalLeft + (twoOriginalRight - twoOriginalLeft) / 2
                && threeBottom < twoOriginalBottom
                && threeBottom > twoOriginalTop + (twoOriginalBottom - twoOriginalTop) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 游戏结束的dialog
     */
    private void gameOverDialog() {
        //成功保存数据,已过的关，和成功的star
        int star = 0;
        int imgState = 0;
        if (stepNumber <= gameData.getBestStep()) {
            star = 3;
            imgState = R.drawable.win3;
        } else if (stepNumber < gameData.getBestStep() + 3 && stepNumber > gameData.getBestStep()) {
            star = 2;
            imgState = R.drawable.win2;
        } else {
            star = 1;
            imgState = R.drawable.win1;
        }
        if (grade.equals(Content.GRADE_ONE)) {
            if (SPCash.getData("success_one_had" + level, false)) {
                //如果这个关卡已经通过了，只保存得心数据
                SPCash.saveData("success_one_star_" + level, star);
            } else {
                //如果这个关卡是第一次通过，保存本关卡的级别，和已通过的状态
                SPCash.saveData("success_one", level);
                SPCash.saveData("success_one_star_" + level, star);
                SPCash.saveData("success_one_had" + level, true);
            }
        }
        GameOverDialog.dialog(this, imgState, new GameOverDialog.GameOverListener() {
            @Override
            public void menuClick(Dialog dialog) {
                clickMusic();
                dialog.dismiss();
                finish();
            }

            @Override
            public void resetClick(Dialog dialog) {
                clickMusic();
                resetData();
                dialog.dismiss();
            }

            @Override
            public void shareClick(Dialog dialog) {
                clickMusic();
                dialog.dismiss();
                resetData();
                ShareUtil.share(GameActivity.this);
            }

            @Override
            public void nextClick(Dialog dialog) {
                clickMusic();
                if (grade.equals(Content.GRADE_ONE)) {
                    if (level < EasyData.getEasyData().size()-1) {
                        level++;
                        gameData = EasyData.getEasyData().get(level);
                        initData();
                        dialog.dismiss();
                    } else {
                        showTost("所有关已通过");
                    }
                }
            }
        });
    }


}
