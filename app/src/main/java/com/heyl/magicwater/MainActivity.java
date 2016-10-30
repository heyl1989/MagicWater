package com.heyl.magicwater;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.heyl.magicwater.base.BaseActivity;
import com.heyl.magicwater.comman.Content;

public class MainActivity extends BaseActivity implements OnClickListener{

    private ImageView imgVol;
    private boolean isMusicPlay = false;

    @Override
    protected void initView() {
        initService();
        setContentView(R.layout.activity_main);

        Button start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);
        imgVol = (ImageView)findViewById(R.id.img_vol);
        imgVol.setOnClickListener(this);
        ImageView imgSetting = (ImageView)findViewById(R.id.img_setting);
        imgSetting.setOnClickListener(this);
    }

    /**
     * 绑定服务
     */
    private void initService() {
        Intent service = new Intent(this,BackgroudIntentService.class);
        bindService(service,serviceConn ,BIND_AUTO_CREATE);
    }
    ServiceConnection serviceConn =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent(Content.START_VOL));
            isMusicPlay = true;
            imgVol.setImageResource(R.drawable.open_vol);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                goOther(SelectGradeActivity.class);
            break;
            case R.id.img_vol:
                if(isMusicPlay){
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Content.STOP_VOL));
                    imgVol.setImageResource(R.drawable.close_vol);
                    isMusicPlay = false;
                }else{
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Content.START_VOL));
                    isMusicPlay = true;
                    imgVol.setImageResource(R.drawable.open_vol);
                }
                break;
            case R.id.img_setting:
                showTost("设置");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConn);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}
