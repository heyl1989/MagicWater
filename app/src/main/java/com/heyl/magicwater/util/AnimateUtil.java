package com.heyl.magicwater.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

/**
 * Created by heyl on 2016/10/26.
 */
public class AnimateUtil {

    private static AnimationEndListener animationEndListener;

    /**
     * 带回调的属性动画
     * @param obj
     * @param x
     * @param y
     * @param startDegree
     * @param endDegree
     */
    public static void rotate(Object obj, int x, int y , float startDegree, float endDegree){
        Log.e("旋转坐标",x+"/"+y);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(obj, "rotation", startDegree, endDegree);
        ((View)obj).setPivotX(x);
        ((View)obj).setPivotY(y);
        rotateAnimator.setDuration(350);
        rotateAnimator.start();
    }

    public static void rotate(Object obj, int x, int y , float startDegree, float endDegree, final AnimationEndListener animationEndListener){
        Log.e("回转坐标",x+"/"+y);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(obj, "rotation", startDegree, endDegree);
        ((View)obj).setPivotX(x);
        ((View)obj).setPivotY(y);
        rotateAnimator.setDuration(350);
        rotateAnimator.start();
        rotateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationEndListener.animationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public interface AnimationEndListener{
        void animationEnd(Animator animation);
    }
}
