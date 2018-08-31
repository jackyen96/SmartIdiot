package com.zhenjie.smartidiot.utils;

import android.util.Log;

/**
 * 文件名: L
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 22:15
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：Log的封装工具类
 */
public class L {
    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "SmartIdiot";

    //五个等级
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }

    public static void f(String text){
        if(DEBUG){
            Log.wtf(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }
}