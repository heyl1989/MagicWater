package com.heyl.magicwater;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heyl.magicwater.App.AppBus;
import com.heyl.magicwater.base.BaseActivity;
import com.heyl.magicwater.model.BusEventData;
import com.heyl.magicwater.util.SizeUtil;
import com.heyl.magicwater.view.AnimImageView;
import com.heyl.magicwater.view.CustomSurfaceView;
import com.heyl.magicwater.view.CustomView;
import com.squareup.otto.Subscribe;

public class GameActivity2 extends BaseActivity implements View.OnClickListener {

    private TextView time;
    private TextView step;
    private TextView title;
    private CustomSurfaceView imgBottleTwo;
    private CustomSurfaceView imgBottleOne;
    private CustomSurfaceView imgBottleThree;
    private TextView tvBottleOne;
    private TextView tvBottleTwo;
    private TextView tvBottleThree;
    private int oneLeft = 0, oneTop = 0, oneRight = 0, oneBottom = 0;
    private int twoLeft = 0, twoTop = 0, twoRight = 0, twoBottom = 0;
    private int threeLeft = 0, threeTop = 0, threeRight = 0, threeBottom = 0;
    private int[] bottle = new int[]{R.drawable.bottle_0, R.drawable.bottle_1, R.drawable.bottle_2, R.drawable.bottle_3, R.drawable.bottle_4,
            R.drawable.bottle_5, R.drawable.bottle_6, R.drawable.bottle_7, R.drawable.bottle_8, R.drawable.bottle_9, R.drawable.bottle_10};


    @Override
    protected void initView() {
        AppBus.getInstance().register(this);
        setContentView(R.layout.activity_game_2);

        //倒计时
        time = (TextView) findViewById(R.id.tv_time);
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
        AnimImageView animImageView = new AnimImageView();
        animImageView.setAnimation(animateImg);
        animImageView.start(true, 200);

        //第一个瓶子
        imgBottleOne = (CustomSurfaceView) findViewById(R.id.cv_bottle_one);
        imgBottleOne.setImageResource(R.drawable.bottle_0);
        imgBottleOne.setXY(SizeUtil.getScreenWith(this) / 2 - SizeUtil.dp2px(this, 20), SizeUtil.dp2px(this, 130));
        tvBottleOne = (TextView) findViewById(R.id.tv_bottle_one);

        //第二个瓶子
        imgBottleTwo = (CustomSurfaceView) findViewById(R.id.cv_bottle_two);
        imgBottleTwo.setImageResource(R.drawable.bottle_0);
        imgBottleTwo.setXY(SizeUtil.dp2px(this, 90), SizeUtil.dp2px(this, 280));
        tvBottleTwo = (TextView) findViewById(R.id.tv_bottle_two);

        //第三个瓶子
        imgBottleThree = (CustomSurfaceView) findViewById(R.id.cv_bottle_three);
        imgBottleThree.setImageResource(R.drawable.bottle_0);
        imgBottleThree.setXY(SizeUtil.dp2px(this, 210), SizeUtil.dp2px(this, 280));
        tvBottleThree = (TextView) findViewById(R.id.tv_bottle_three);

        //返回按钮
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        initData();

    }

    /**
     * 初始数据
     */
    private void initData() {
        title.setText("向" + 4 + "L的瓶装入" + 3 + "L水");

        imgBottleOne.setVisibility(View.GONE);
        tvBottleOne.setText("");
        tvBottleOne.setVisibility(View.INVISIBLE);

        imgBottleTwo.zoomBitmap(1f);
        tvBottleTwo.setText("0/4");

        imgBottleThree.zoomBitmap(3 / 4.0f);
        tvBottleThree.setText("0/3");
    }

    /**
     * 接受BUS数据
     *
     * @param data
     */
    @Subscribe
    public void getPosition(BusEventData data) {
        //操作的是第一个瓶子
        if (data.getCustomSurfaceView().equals(imgBottleOne)) {
            oneLeft = data.getLeft();
            oneTop = data.getTop();
            oneRight = data.getRight();
            oneBottom = data.getBottom();
            if (data.isOriginal()) {
                return;
            }
            if (imgBottleOne.getImageResource() != R.drawable.bottle_0) {
                //去装第二个瓶子
                if (oneTop > twoTop - dp2px(10) && oneTop < twoBottom - dp2px(50) &&
                        (oneLeft + dp2px(10) < twoRight - dp2px(10) && oneLeft > twoLeft
                                || oneRight - dp2px(10) > twoLeft + dp2px(10) && oneRight < twoRight)) {

                }
                //去装第三个瓶子
                if (oneTop > threeTop - dp2px(10) && oneTop < threeBottom - dp2px(50) &&
                        (oneLeft + dp2px(10) < threeRight - dp2px(10) && oneLeft > threeLeft
                                || oneRight - dp2px(10) > threeLeft + dp2px(10) && oneRight < threeRight)) {

                }

            }
            Log.e("我是第一个瓶子", "/" + data.getLeft());
        }
        //操作的是第二个瓶子
        if (data.getCustomSurfaceView().equals(imgBottleTwo)) {
            twoLeft = data.getLeft();
            twoTop = data.getTop();
            twoRight = data.getRight();
            twoBottom = data.getBottom();
            if (data.isOriginal()) {
                return;
            }
            if (imgBottleTwo.getImageResource() != R.drawable.bottle_0) {
                //去装第一个瓶子
                if (twoTop > oneTop - dp2px(10) && twoTop < oneBottom - dp2px(50) &&
                        (twoLeft + dp2px(10) < oneRight - dp2px(10) && twoLeft > oneLeft
                                || twoRight - dp2px(10) > oneLeft + dp2px(10) && twoRight < oneRight)) {

                }
                //去装第三个瓶子
                if (twoTop > threeTop - dp2px(10) && twoTop < threeBottom - dp2px(50) &&
                        (twoLeft + dp2px(10) < threeRight - dp2px(10) && twoLeft > threeLeft
                                || twoRight - dp2px(10) > threeLeft + dp2px(10) && twoRight < threeRight)) {

                    imgBottleTwo.setImageResource(R.drawable.bottle_2);
                    imgBottleThree.setImageResource(R.drawable.bottle_10);
                    return;
                }

            }
            Log.e("我是第二个瓶子", "/" + twoLeft + "/" + threeLeft);
        }
        //操作的是第三个瓶子
        if (data.getCustomSurfaceView().equals(imgBottleThree)) {
            threeLeft = data.getLeft();
            threeTop = data.getTop();
            threeRight = data.getRight();
            threeBottom = data.getBottom();
            if (data.isOriginal()) {
                return;
            }
            if (imgBottleThree.getImageResource() != R.drawable.bottle_0) {
                //去装第一个瓶子
                if (threeTop > oneTop - dp2px(10) && threeTop < oneBottom - dp2px(50) &&
                        (threeLeft + dp2px(10) < oneRight - dp2px(10) && threeLeft > oneLeft
                                || threeRight - dp2px(10) > oneLeft + dp2px(10) && threeRight < oneRight)) {

                }
                //去装第二个瓶子
                if (threeTop > twoTop - dp2px(10) && threeTop < twoBottom - dp2px(50) &&
                        (threeLeft + dp2px(10) < twoRight - dp2px(10) && threeLeft > twoLeft
                                || threeRight - dp2px(10) > twoLeft + dp2px(10) && threeRight < twoRight)) {
                    imgBottleTwo.setImageResource(R.drawable.bottle_8);
                    imgBottleThree.setImageResource(R.drawable.bottle_0);
                }

            }
            Log.e("我是第三个瓶子", "/" + data.getLeft());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        AppBus.getInstance().unregister(this);
        imgBottleOne.recyle();
        imgBottleTwo.recyle();
        imgBottleThree.recyle();
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
}
