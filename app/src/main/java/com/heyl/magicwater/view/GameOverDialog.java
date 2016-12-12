package com.heyl.magicwater.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heyl.magicwater.R;

/**
 * Created by heyl on 2016/11/13.
 */

public class GameOverDialog {

    /**
     * 游戏结束的弹框
     */
    public static void dialog(Activity context, int imgstate,final GameOverListener gameOverListener) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.dialog_game_over, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(context,R.style.dialog).create();
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setContentView(layout);

        //状态图片
        ImageView imgState = (ImageView) layout.findViewById(R.id.img_game_over_state);
        imgState.setImageResource(imgstate);

        //菜单按钮
        Button btnMenu = (Button) layout.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameOverListener.menuClick(dialog);
            }
        });

        //复位按钮
        Button btnReset = (Button) layout.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameOverListener.resetClick(dialog);
            }
        });

        //分享按钮
        Button btnShare = (Button) layout.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameOverListener.shareClick(dialog);
            }
        });

        //下一个按钮
        Button btnNext = (Button) layout.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameOverListener.nextClick(dialog);
            }
        });
    }

    public interface GameOverListener{
        void menuClick(Dialog dialog);
        void resetClick(Dialog dialog);
        void shareClick(Dialog dialog);
        void nextClick(Dialog dialog);
    }

}
