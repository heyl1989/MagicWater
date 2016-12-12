package com.heyl.magicwater.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.heyl.magicwater.R;

/**
 * Created by heyl on 2016/11/13.
 */

public class GameHelpDialog {

    public static void show(Activity context){

        LayoutInflater inflaterDl = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.dialog_raiders, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(context,R.style.dialog).create();
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setContentView(layout);

        Button cancel = (Button) layout.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
