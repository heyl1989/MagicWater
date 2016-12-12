package com.heyl.magicwater;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heyl.magicwater.base.BaseActivity;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class SettingActivity extends BaseActivity {

    private TextView appVersion;
    private TextView sendEmail;
    private TextView checkVersion;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);

        appVersion = (TextView)findViewById(R.id.tv_appversion);
        initVersion();
        sendEmail = (TextView) findViewById(R.id.tv_send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMusic();
                String[] reciver = new String[] { "heyl2804@gmail.com" };
                Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
                myIntent.setType("plain/text");
                myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
                startActivity(Intent.createChooser(myIntent, "选择邮箱"));
            }
        });
        checkVersion = (TextView)findViewById(R.id.tv_check_version);
        checkVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMusic();
                updateVersion();
            }
        });


    }

    private void updateVersion() {
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                // TODO Auto-generated method stub
                if (updateStatus == UpdateStatus.Yes) {//版本有更新
                    //BmobUpdateAgent.forceUpdate(SettingActivity.this);
                }else if(updateStatus == UpdateStatus.No){//"版本无更新"
                    showTost("已经是最新版本了");
                }
            }
        });
        //发起自动更新
        BmobUpdateAgent.update(this);
    }

    private void initVersion() {
        String versionName = "";
        try {
            String pkName = this.getPackageName();
            versionName = getPackageManager().getPackageInfo(pkName,0).versionName;
        }catch (Exception e){
            e.printStackTrace();
        }
        appVersion.setText("魔幻香水 "+versionName);
    }
}
