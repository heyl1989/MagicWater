package com.heyl.magicwater.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.heyl.magicwater.App.MyApplication;

/**
 * Created by heyl on 2016/10/26.
 */
public class SPCash {

    private static SharedPreferences sp = MyApplication.getInstance()
            .getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    /**
     * 存入数据
     */
    public static void saveData(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 取出数据
     */
    public static int getData(String key, int value) {
        return sp.getInt(key, value);
    }

    /**
     * 查询某个值是否存在
     */
    public static boolean isContainKey(String key) {
        return sp.contains(key);
    }
    /**
     *删除key
     */
    public static void clearKey(String key) {
        sp.edit().remove(key).commit();
    }
    /**
     * 删除所有
     */
    public static void clearAll() {
        sp.edit().clear().commit();
    }


}
