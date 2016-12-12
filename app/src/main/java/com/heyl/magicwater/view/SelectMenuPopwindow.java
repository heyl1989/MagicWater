package com.heyl.magicwater.view;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.heyl.magicwater.R;
import com.heyl.magicwater.base.BasePopupWindow;
import com.heyl.magicwater.util.SizeUtil;

/**
 * Created by heyl on 2016/11/13.
 */

public class SelectMenuPopwindow extends BasePopupWindow implements View.OnClickListener {

    private int[] viewLocation;
    private OnCommentPopupClickListener mOnCommentPopupClickListener;
    private ImageView imgMenu;
    private ImageView imgRestart;
    private ImageView imgRegulation;

    public SelectMenuPopwindow(Activity context) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public SelectMenuPopwindow(Activity context, int w, int h) {
        super(context, w, h);

        viewLocation = new int[2];

        imgMenu = (ImageView) mPopupView.findViewById(R.id.img_menu);
        imgMenu.setOnClickListener(this);
        imgRestart = (ImageView) mPopupView.findViewById(R.id.img_restart);
        imgRestart.setOnClickListener(this);
        imgRegulation = (ImageView) mPopupView.findViewById(R.id.img_regulation);
        imgRegulation.setOnClickListener(this);

        isDismiss();

    }

    @Override
    public void showPopupWindow(View v) {
        try {
            //得到v的位置
            v.getLocationOnScreen(viewLocation);
            //展示位置：
            //参照点为view的右上角，偏移值为：x方向距离参照view的一定倍数距离
            //垂直方向自身减去popup自身高度的一半（确保在中间）
            mPopupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, (int) (v.getWidth() * 1.8),
                    viewLocation[1] - SizeUtil.dp2px(mContext, 15f));

            if (getShowAnimation() != null && getAnimaView() != null) {
                getAnimaView().startAnimation(getShowAnimation());
            }
        } catch (Exception e) {
            Log.w("error", "error");
        }
    }

    /**
     * 判断dialog是否显示
     */
    private void isDismiss() {

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mOnCommentPopupClickListener.onPopDismiss();
            }
        });
    }
    public void dissmiss(){
        if(null != mPopupWindow && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }


    @Override
    protected Animation getShowAnimation() {
        return getScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
    }

    @Override
    public Animation getExitAnimation() {
        return getScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
    }

    @Override
    public View getPopupView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pop_select, null);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.ll_popup_contianer);
    }

    public OnCommentPopupClickListener getOnCommentPopupClickListener() {
        return mOnCommentPopupClickListener;
    }

    public void setOnCommentPopupClickListener(OnCommentPopupClickListener onCommentPopupClickListener) {
        mOnCommentPopupClickListener = onCommentPopupClickListener;
    }

    //=============================================================clickEvent
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onMenuClick();
                    dismiss();
                }
                break;
            case R.id.img_restart:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onReStartClick();
                    dismiss();
                }
                break;
            case R.id.img_regulation:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onRegulationClick();
                    dismiss();
                }
                break;
        }
    }

    //=============================================================InterFace
    public interface OnCommentPopupClickListener {
        void onMenuClick();

        void onReStartClick();

        void onRegulationClick();

        void onPopDismiss();
    }
    //=============================================================abortMethods


    @Override
    protected View getClickToDismissView() {
        return null;
    }
}
