package com.heyl.magicwater.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.heyl.magicwater.App.AppBus;
import com.heyl.magicwater.R;
import com.heyl.magicwater.comman.Content;
import com.heyl.magicwater.model.BusEventData;
import com.heyl.magicwater.util.AnimateUtil;
import com.heyl.magicwater.util.SPCash;

/**
 * 自定义的view，需要覆盖onDraw()方法绘制控件，覆盖onTouchEvent()接收触摸消息
 */
public class CustomView extends View {

    private Rect rectf;
    private Rect old;
    private Matrix matrix;
    private int deltaX, deltaY;//点击位置和图形边界的偏移量
    private Context context;
    private Bitmap bitmap;
    private float zoom = 1f;
    private int resourceId = R.drawable.bottle_0;
    //    private int originalX;
//    private int originalY;
    private int count;//双击事件计数器
    private long firClick, secClick;//双击事件
    private int bpheight, bpwidth;
    private int left;
    private int top;
    private int right;
    private int bottom;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        matrix = new Matrix();
        //为bitmap定义的矩形
        rectf = new Rect();
        old = new Rect(rectf);
    }


    /**
     * 初始化
     */
    private void init() {
        //回收没有用的bitmap
        if (null != bitmap) {
            if(!bitmap.isRecycled()){
                bitmap.recycle();
                bitmap = null;
                Log.e("BITMAP","bitmap回收了");
            }
        }

        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        //设置缩放图片参数
        matrix.postScale(zoom, zoom);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bpwidth = bitmap.getWidth();
        bpheight = bitmap.getHeight();
        rectf.top = top;
        rectf.left = left;
        rectf.right = left + bpwidth;
        rectf.bottom = top + bpheight;
        invalidate(rectf);
        //发出初始化数据
        AppBus.getInstance().post(new BusEventData(this, left, top, left + bpwidth, top + bpheight, true));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rectf, null);
        //回收内存，好像没效果
        destroyDrawingCache();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!rectf.contains(x, y)) {
                    return false;//没有在图片上点击，不处理触摸消息
                }
                //双击
                if (resourceId != R.drawable.bottle_0) {
                    int rotateX = (int) ((rectf.right - rectf.left) / 2.0f) + rectf.left;
                    int rotateY = (int) ((rectf.bottom - rectf.top) / 2.0f) + rectf.top;
                    dobleClick(rotateX, rotateY);
                }
                //点击位置和图形边界的偏移量
                deltaX = x - rectf.left;
                deltaY = y - rectf.top;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                holdWater(x);
                //更新矩形的位置
                rectf.left = x - deltaX;
                rectf.top = y - deltaY;
                rectf.right = rectf.left + bpwidth;
                rectf.bottom = rectf.top + bpheight;
                left = rectf.left;
                top = rectf.top;
                right = rectf.right;
                bottom = rectf.bottom;
                AppBus.getInstance().post(new BusEventData(CustomView.this, left, top, right, bottom, false));
                old.union(rectf);//要刷新的区域，求新矩形区域与旧矩形区域的并集
                invalidate(old);//出于效率考虑，设定脏区域，只进行局部刷新，不是刷新整个view

                break;
        }
        return true;//处理了触摸消息，消息不再传递
    }

    /**
     * 双击事件
     */
    private boolean dobleClick(final int x, final int y) {
        count++;
        if (count == 1) {
            firClick = System.currentTimeMillis();
        } else if (count == 2) {
            secClick = System.currentTimeMillis();
            if (secClick - firClick < 800) {
                //双击事件
                Log.e("五角星", "双击了");
                pourWaterRotate(x, y, R.drawable.bottle_0);
            }
            count = 0;
            firClick = 0;
            secClick = 0;
        }
        return true;
    }

    /**
     * 倒水旋转动画
     */
    public void pourWaterRotate(final int x, final int y, final int resourceId) {
        AnimateUtil.rotate(this, x, y, 0f, 120f, new AnimateUtil.AnimationEndListener() {
            @Override
            public void animationEnd(Animator animiator) {
                AnimateUtil.rotate(CustomView.this, x, y, 120f, 0f);
                //AppBus.getInstance().post(new BusEventData(CustomView.this,left,top,left+bpwidth,top+bpheight));
                setImageResource(resourceId);
            }
        });
    }

    /**
     * 空瓶接近水管装水
     */
    private void holdWater(int x) {
        if ((x - deltaX) <= SPCash.getData(Content.WATER_WIDTH, 0) && resourceId != R.drawable.bottle_10) {
            //Log.e("五角星",outRight+"'/"+(x - deltaX));
            zoom = 1f;
            setImageResource(R.drawable.bottle_10);
        }
    }

    /**
     * 对bitmap进行缩放
     */
    public void zoomBitmap(float zoom) {
        this.zoom = zoom;
        init();
        //invalidate();
    }

    /**
     * 设置初始位置
     *
     * @param x
     * @param y
     */
    public void setXY(int x, int y) {
        this.left = x;
        this.top = y;
        init();
        //invalidate();
    }

    /**
     * 修改bitmap图片
     */
    public void setImageResource(int resourceId) {
        this.resourceId = resourceId;
        zoom = 1.0f;
        init();
        //invalidate();
    }

    /**
     * 获取当前对象的img
     */
    public int getImageResource() {
        return resourceId;
    }

    /**
     * 回收对象
     */
    public void recyle() {
        rectf = null;
        matrix = null;
        old = null;
        bitmap.recycle();
        bitmap = null;
        System.gc();
        //destroyDrawingCache();
    }


}
