package com.heyl.magicwater.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    protected abstract void initView();

    /**
     * 自定义吐司
     * @param s
     */
    protected void showTost(String s){
        Toast toast = new Toast(this);
        TextView text = new TextView(this);
        text.setTextSize(25);
        text.setTextColor(Color.WHITE);
        text.setText(s);
        toast.setView(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

    protected void goOther(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    protected void goOther(Class<?> cls, Bundle bundle){
        Intent intent = new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
