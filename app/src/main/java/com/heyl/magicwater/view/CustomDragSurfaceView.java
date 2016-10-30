package com.heyl.magicwater.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.heyl.magicwater.R;

/**
 * Created by heyl on 2016/10/29.
 */
public class CustomDragSurfaceView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private Context context;
    private SurfaceHolder holder;
    private Bitmap icon;
    private Paint paint;
    private boolean running = true;
    private Point point = new Point();//点击点
    private Rect rect;//图片的rect
    private boolean canDrag = false;//判断是否点击在图片上，否则拖动无效
    private int offsetX = 0, offsetY = 0;//点击点离图片左上角的距离

    public CustomDragSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        holder = this.getHolder();//获取holder
        holder.addCallback(this);
        this.setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottle_0);
        rect = new Rect(0,0,icon.getWidth(),icon.getHeight());

        paint=new Paint();
        running=true;
        draw(holder);

    }

    private void draw(final SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    //开始画的时间    long start=System.currentTimeMillis();
                    Canvas canvas = holder.lockCanvas();//获取画布
                    try {
                        if(canvas != null){
                            synchronized (holder) {
                                canvas.drawColor(Color.BLACK);
                                canvas.drawBitmap(icon, rect.left,rect.top,null);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }

                    // holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
                    //结束的时间   long end=System.currentTimeMillis();
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running=false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            //手按下的时候
            case MotionEvent.ACTION_DOWN:
                point.x = (int) event.getX();
                point.y = (int) event.getY();
                if (rect.contains(point.x, point.y)) {
                    canDrag = true;
                    offsetX = point.x - rect.left;
                    offsetY = point.y - rect.top;
                }
                break;

            //移动的时候
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    rect.left = (int) event.getX() - offsetX;
                    rect.top = (int) event.getY() - offsetY;
                    rect.right = rect.left + icon.getWidth();
                    rect.bottom = rect.top + icon.getHeight();
                    if (rect.left < 0) {
                        rect.left = 0;
                        rect.right = rect.left + icon.getWidth();
                    }
                    if (rect.right > getMeasuredWidth()) {
                        rect.right = getMeasuredWidth();
                        rect.left = rect.right - icon.getWidth();
                    }
                    if (rect.top < 0) {
                        rect.top = 0;
                        rect.bottom = rect.top + icon.getHeight();
                    }
                    if (rect.bottom > getMeasuredHeight()) {
                        rect.bottom = getMeasuredHeight();
                        rect.top = rect.bottom - icon.getHeight();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                canDrag = false;
                break;

            default:
                break;
        }
        return true;
    }
}
