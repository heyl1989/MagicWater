package com.heyl.magicwater.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.heyl.magicwater.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by heyl on 2016/11/13.
 */

public class ShareUtil {

    private static PopupWindow bottomWindow;

    public  static void share(Context context){
        UMImage image = new UMImage(context, R.drawable.share);//资源文件
//        new ShareAction((Activity)context).withText("hello")
//                .withMedia(image).share();

       new ShareAction((Activity)context)
               .withTitle("魔幻香水")
               .withText("我正在玩一个有趣的水瓶装水游戏，一起来玩吧！")
               .withMedia(image)
               .withTargetUrl("https://www.pgyer.com/050K")
               .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA).open();

    }



}
