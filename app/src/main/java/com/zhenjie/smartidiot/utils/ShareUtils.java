package com.zhenjie.smartidiot.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;


/**
 * 文件名: ShareUtils
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 22:45
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：SharedPreference工具类
 */
public class ShareUtils {

    public static final String NAME = "config";

    public static void putString(@NotNull Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getString(@NotNull Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putInt(@NotNull Context mContext, String key, int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(@NotNull Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean(@NotNull Context mContext, String key, boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(@NotNull Context mContext,String key,boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public static void deleteShare(@NotNull Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    public static void deleteAll(@NotNull Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear();
    }
}